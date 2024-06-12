package br.com.template.statemachine.service;

import br.com.template.model.Usuario;
import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.statemachine.StateMachine;

public interface StateMachineEventService {

    StateMachine<UsuarioStates, UsuarioEvents> buildSM(Usuario usuario);
    void sendEvent(Usuario usuario, UsuarioEvents event);
    void sendEvent(Usuario usuario, UsuarioEvents event, MessageHeaderAccessor messageHeaderAccessor);

}
