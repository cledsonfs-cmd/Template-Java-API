package br.com.template.statemachine;

import br.com.template.service.UsuarioStatesService;
import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<UsuarioStates, UsuarioEvents> {

    private final UsuarioStatesService service;

    @Override
    public void configure(StateMachineStateConfigurer<UsuarioStates, UsuarioEvents> states) throws Exception {
        states
                .withStates()
                .initial(UsuarioStates.INICIO)
                .states(EnumSet.allOf(UsuarioStates.class))
                .end(UsuarioStates.FIM);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UsuarioStates, UsuarioEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(UsuarioStates.INICIO)
                .target(UsuarioStates.ATIVO)
                .event(UsuarioEvents.ATIVAR)
                .guard(service::guardFazerAlgo)
                .action(service::actionAtivar)
                .and().withExternal()

                .source(UsuarioStates.ATIVO)
                .target(UsuarioStates.SUSPENSO)
                .event(UsuarioEvents.SUSPENDER)
                .action(service::actionSuspender)
                .and().withExternal()

                .source(UsuarioStates.SUSPENSO)
                .target(UsuarioStates.INATIVO)
                .event(UsuarioEvents.INATIVAR)
                .action(service::actionInativar)
                .and().withExternal()

                .source(UsuarioStates.INATIVO)
                .target(UsuarioStates.FIM)
                .event(UsuarioEvents.EXCLUIR)
                .action(service::actionExcluir)
                .and().withExternal()
            ;

    }
}
