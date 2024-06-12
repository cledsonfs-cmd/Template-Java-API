package br.com.template.statemachine.service.impl;

import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import br.com.template.statemachine.service.StateMachineErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StateMachineErrorServiceImpl implements StateMachineErrorService {

    private final String VARIABLE_ERRORS = "ERROS";

    public void addError(StateContext<UsuarioStates, UsuarioEvents> state, String error) {
        List<String> errors = getErrors(state.getExtendedState());
        errors.add(error);
        state.getExtendedState().getVariables().put(VARIABLE_ERRORS, errors);
    }

    @SuppressWarnings("unchecked")
    public List<String> getErrors(ExtendedState extendedState) {
        Object obj = extendedState.getVariables().get(VARIABLE_ERRORS);
        return obj != null ? (List<String>) extendedState.getVariables().get(VARIABLE_ERRORS) : new ArrayList<>();
    }
}
