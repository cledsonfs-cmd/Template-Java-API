package br.com.template.statemachine.interceptors;

import br.com.template.model.Usuario;
import br.com.template.repository.UsuarioRepository;
import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import br.com.template.statemachine.service.StateMachineErrorService;
import br.gov.ce.sop.conserva.model.entity.Medicao;
import br.gov.ce.sop.conserva.model.entity.MedicaoHistorico;
import br.gov.ce.sop.conserva.model.entity.TipoHistoricoMedicao;
import br.gov.ce.sop.conserva.model.repository.MedicaoRepository;
import br.gov.ce.sop.conserva.model.service.MedicaoHistoricoService;
import br.gov.ce.sop.conserva.security.dto.AutenticatedUser;
import br.gov.ce.sop.conserva.statemachine.enums.MedicaoEvents;
import br.gov.ce.sop.conserva.statemachine.enums.MedicaoStatesFisico;
import br.gov.ce.sop.conserva.statemachine.service.StateMachineErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class StatusMedicaoChangeInterceptor extends StateMachineInterceptorAdapter<UsuarioStates, UsuarioEvents> {

    private final UsuarioRepository medicaoRepository;
    private final StateMachineErrorService errorService;
    private final MedicaoHistoricoService historicoService;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void preStateChange(State<UsuarioStates, UsuarioEvents> state, Message<UsuarioEvents> message, Transition<UsuarioStates, UsuarioEvents> transition, StateMachine<UsuarioStates, UsuarioEvents> stateMachine, StateMachine<UsuarioStates, UsuarioEvents> rootStateMachine) {
        List<String> errors = errorService.getErrors(stateMachine.getExtendedState());
        if (errors.isEmpty()) {
            Optional.ofNullable(message).flatMap(msg -> Optional.ofNullable((Long) msg.getHeaders().getOrDefault(message.getPayload().getMedicao().getId(), -1L)))
                    .ifPresent(idMedicao -> {
                        Usuario usuario = message.getPayload().getUsuario();
                        usuario.setIdStatusFisico(transition.getTarget().getId().getId());

                        medicao = Objects.requireNonNull(message).getPayload().getMedicao();
                        MedicaoHistorico medicaoHistorico = MedicaoHistorico.builder()
                                .medicao(medicao)
                                .dataHora(LocalDateTime.now())
                                .tipoHistoricoMedicao(TipoHistoricoMedicao.builder()
                                        .id(message.getPayload().getMedicaoHistoricoDTO().getIdTipoHistorico())
                                        .build())
                                .matricula(AutenticatedUser.getInstance().getUserName())
                                .observacao(message.getPayload().getMedicaoHistoricoDTO().getObservacao())
                                .build();
                        historicoService.save(medicaoHistorico);
                    });
        }
    }

}
