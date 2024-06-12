package br.com.template.service;

import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.enums.UsuarioStates;
import org.springframework.statemachine.StateContext;

public interface UsuarioStatesService {

    void actionAtivar(StateContext<UsuarioStates, UsuarioEvents> state);

    void actionSuspender(StateContext<UsuarioStates, UsuarioEvents> state);

    void actionInativar(StateContext<UsuarioStates, UsuarioEvents> state);

    void actionExcluir(StateContext<UsuarioStates, UsuarioEvents> state);

    boolean guardFazerAlgo(StateContext<UsuarioStates, UsuarioEvents> state);
}
