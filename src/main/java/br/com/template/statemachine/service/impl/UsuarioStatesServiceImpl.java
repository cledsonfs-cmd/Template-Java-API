package br.com.template.statemachine.service.impl;

import br.com.template.enums.UsuarioEvents;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.entity.Usuario;
import br.com.template.repository.UsuarioRepository;
import br.com.template.statemachine.service.UsuarioStatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioStatesServiceImpl implements UsuarioStatesService {

    private final UsuarioRepository repository;

    @Override
    public void actionAtivar(StateContext<UsuarioStates, UsuarioEvents> state) {
        Usuario usuario = state.getEvent().getUsuario();
        usuario.setIdStatus(UsuarioStates.ATIVO.getId());
        repository.save(usuario);
    }

    @Override
    public void actionSuspender(StateContext<UsuarioStates, UsuarioEvents> state) {
        Usuario usuario = state.getEvent().getUsuario();
        usuario.setIdStatus(UsuarioStates.SUSPENSO.getId());
        repository.save(usuario);
    }

    @Override
    public void actionInativar(StateContext<UsuarioStates, UsuarioEvents> state) {
        Usuario usuario = state.getEvent().getUsuario();
        usuario.setIdStatus(UsuarioStates.INATIVO.getId());
        repository.save(usuario);
    }

    @Override
    public void actionExcluir(StateContext<UsuarioStates, UsuarioEvents> state) {
        Usuario usuario = state.getEvent().getUsuario();
        repository.delete(usuario);
    }

    @Override
    public boolean guardFazerAlgo(StateContext<UsuarioStates, UsuarioEvents> state) {
        return false;
    }
}
