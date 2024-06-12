package br.com.template.statemachine.service;

import br.gov.ce.sop.conserva.statemachine.enums.MedicaoEvents;
import br.gov.ce.sop.conserva.statemachine.enums.MedicaoStatesFisico;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;

import java.util.List;

public interface StateMachineErrorService {

    void addError(StateContext<MedicaoStatesFisico, MedicaoEvents> state, String error);
    List<String> getErrors(ExtendedState extendedState);

}
