package br.com.template.statemachine.service;

import br.com.template.enums.UsuarioEvents;
import br.com.template.enums.UsuarioStates;
import org.springframework.statemachine.StateContext;

public interface UsuarioStatesService {

    void actionAtivar(StateContext<UsuarioStates, UsuarioEvents> state);

    void actionSuspender(StateContext<UsuarioStates, UsuarioEvents> state);

    void actionInativar(StateContext<UsuarioStates, UsuarioEvents> state);

    void actionExcluir(StateContext<UsuarioStates, UsuarioEvents> state);

    boolean guardFazerAlgo(StateContext<UsuarioStates, UsuarioEvents> state);
}
