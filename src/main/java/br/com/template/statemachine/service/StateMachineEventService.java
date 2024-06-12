package br.com.template.statemachine.service;

import br.gov.ce.sop.conserva.model.entity.Medicao;
import br.gov.ce.sop.conserva.statemachine.enums.MedicaoEvents;
import br.gov.ce.sop.conserva.statemachine.enums.MedicaoStatesFisico;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.statemachine.StateMachine;

public interface StateMachineEventService {

    StateMachine<MedicaoStatesFisico, MedicaoEvents> buildSM(Medicao medicao);
    void sendEvent(Medicao medicao, MedicaoEvents event);
    void sendEvent(Medicao medicao, MedicaoEvents event, MessageHeaderAccessor messageHeaderAccessor);

}
