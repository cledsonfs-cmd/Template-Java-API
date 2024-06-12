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
                .target(UsuarioStates.STATUS_1)
                .event(UsuarioEvents.FAZER_ALGO)
                .guard(service::guardFazerAlgo)
                .action(service::actionFazerAlgo)
                .and().withExternal()

                .source(UsuarioStates.STATUS_1)
                .target(UsuarioStates.STATUS_2)
                .event(UsuarioEvents.FAZER_OUTRA_COISA)
                .action(service::actionFazerOutraCoisa)
                .and().withExternal()
            ;

    }
}
