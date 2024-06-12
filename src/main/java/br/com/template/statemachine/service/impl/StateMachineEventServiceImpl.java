package br.com.template.statemachine.service.impl;

import br.gov.ce.sop.conserva.api.exception.StateMachineException;
import br.gov.ce.sop.conserva.model.entity.Medicao;
import br.gov.ce.sop.conserva.statemachine.enums.MedicaoEvents;
import br.gov.ce.sop.conserva.statemachine.enums.MedicaoStatesFisico;
import br.gov.ce.sop.conserva.statemachine.interceptors.StatusMedicaoChangeInterceptor;
import br.gov.ce.sop.conserva.statemachine.service.StateMachineErrorService;
import br.gov.ce.sop.conserva.statemachine.service.StateMachineEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateMachineEventServiceImpl implements StateMachineEventService {

    private final StateMachineFactory<MedicaoStatesFisico, MedicaoEvents> factory;
    private final StatusMedicaoChangeInterceptor interceptor;
    private final StateMachineErrorService stateMachineErrorService;

    @Override
    public StateMachine<MedicaoStatesFisico, MedicaoEvents> buildSM(Medicao medicao) {
        MedicaoStatesFisico status = MedicaoStatesFisico.of(medicao.getIdStatusFisico());

        StateMachine<MedicaoStatesFisico, MedicaoEvents> sm = factory.getStateMachine(medicao.getIdStatusFisico().toString());

        sm.stopReactively().block();

        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(interceptor);
            sma.resetStateMachineReactively(new DefaultStateMachineContext<>(status, null, null, null)).block();
        });

        sm.startReactively().block();

        return sm;
    }

    @Override
    public void sendEvent(Medicao medicao, MedicaoEvents event) {
        StateMachine<MedicaoStatesFisico, MedicaoEvents> sm = buildSM(medicao);
        event.setMedicao(medicao);

        sm.sendEvent(Mono.just(MessageBuilder
                        .withPayload(event)
                        .build()))
                .subscribe();
        List<String> erros = stateMachineErrorService.getErrors(sm.getExtendedState());
        if (!erros.isEmpty()) {
            throw new StateMachineException(erros);
        }

    }

    @Override
    public void sendEvent(Medicao medicao, MedicaoEvents event, MessageHeaderAccessor messageHeaderAccessor){
        StateMachine<MedicaoStatesFisico, MedicaoEvents> sm = buildSM(medicao);
        event.setMedicao(medicao);

        sm.sendEvent(Mono.just(MessageBuilder
                        .withPayload(event)
                        .setHeaders(messageHeaderAccessor)
                        .build()))
                .subscribe();
        List<String> erros = stateMachineErrorService.getErrors(sm.getExtendedState());
        if (!erros.isEmpty()) {
            throw new StateMachineException(erros);
        }

    }
}
