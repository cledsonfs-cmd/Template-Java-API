package br.com.template.statemachine.service;

import br.com.template.enums.UsuarioEvents;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.entity.Usuario;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.statemachine.StateMachine;

public interface StateMachineEventService {

    StateMachine<UsuarioStates, UsuarioEvents> buildSM(Usuario usuario);
    void sendEvent(Usuario usuario, UsuarioEvents event);
    void sendEvent(Usuario usuario, UsuarioEvents event, MessageHeaderAccessor messageHeaderAccessor);

}
