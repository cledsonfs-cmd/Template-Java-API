package br.com.template.statemachine.service.impl;

import br.com.template.model.Usuario;
import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import br.com.template.statemachine.interceptors.StatusMedicaoChangeInterceptor;
import br.com.template.statemachine.service.StateMachineErrorService;
import br.com.template.statemachine.service.StateMachineEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineException;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateMachineEventServiceImpl implements StateMachineEventService {

    private final StateMachineFactory<UsuarioStates, UsuarioEvents> factory;
    private final StatusMedicaoChangeInterceptor interceptor;
    private final StateMachineErrorService stateMachineErrorService;

    @Override
    public StateMachine<UsuarioStates, UsuarioEvents> buildSM(Usuario usuario) {
        UsuarioStates status = UsuarioStates.of(usuario.getIdStatus());

        StateMachine<UsuarioStates, UsuarioEvents> sm = factory.getStateMachine(usuario.getIdStatus().toString());

        sm.stopReactively().block();

        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(interceptor);
            sma.resetStateMachineReactively(new DefaultStateMachineContext<>(status, null, null, null)).block();
        });

        sm.startReactively().block();

        return sm;
    }

    @Override
    public void sendEvent(Usuario usuario, UsuarioEvents event) {
        StateMachine<UsuarioStates, UsuarioEvents> sm = buildSM(usuario);
        event.setUsuario(usuario);

        sm.sendEvent(Mono.just(MessageBuilder
                        .withPayload(event)
                        .build()))
                .subscribe();
        List<String> erros = stateMachineErrorService.getErrors(sm.getExtendedState());
        if (!erros.isEmpty()) {
            throw new StateMachineException(String.valueOf(erros));
        }

    }

    @Override
    public void sendEvent(Usuario usuario, UsuarioEvents event, MessageHeaderAccessor messageHeaderAccessor){
        StateMachine<UsuarioStates, UsuarioEvents> sm = buildSM(usuario);
        event.setUsuario(usuario);

        sm.sendEvent(Mono.just(MessageBuilder
                        .withPayload(event)
                        .setHeaders(messageHeaderAccessor)
                        .build()))
                .subscribe();
        List<String> erros = stateMachineErrorService.getErrors(sm.getExtendedState());
        if (!erros.isEmpty()) {
            throw new StateMachineException(String.valueOf(erros));
        }

    }
}
