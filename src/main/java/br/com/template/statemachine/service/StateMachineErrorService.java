package br.com.template.statemachine.service;

import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;

import java.util.List;

public interface StateMachineErrorService {

    void addError(StateContext<UsuarioStates, UsuarioEvents> state, String error);
    List<String> getErrors(ExtendedState extendedState);

}
