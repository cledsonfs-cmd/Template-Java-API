package br.com.template.service.impl;

import br.com.template.model.UsuarioHistorico;
import br.com.template.repository.HistoricoUsuarioRepository;
import br.com.template.service.HistoricoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoUsuarioServiceImpl implements HistoricoUsuarioService {

    @Autowired
    private HistoricoUsuarioRepository repository;

    @Override
    public UsuarioHistorico salvar(UsuarioHistorico historicoUsuario) {
        return null;
    }

    @Override
    public UsuarioHistorico buscarPorId(Long id) {
        return null;
    }

    @Override
    public List<UsuarioHistorico> buscarTodos() {
        return List.of();
    }

    @Override
    public void excluir(Long id) {

    }

    @Override
    public UsuarioHistorico atualizar(UsuarioHistorico historicoUsuario) {
        return null;
    }
}
