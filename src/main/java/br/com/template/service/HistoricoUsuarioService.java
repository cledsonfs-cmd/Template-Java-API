package br.com.template.service;
import br.com.template.model.entity.UsuarioHistorico;

import java.util.List;

public interface HistoricoUsuarioService {

    UsuarioHistorico salvar(UsuarioHistorico historicoUsuario);

    UsuarioHistorico buscarPorId(Long id);

    List<UsuarioHistorico> buscarTodos();

    void excluir(Long id);

    UsuarioHistorico atualizar(UsuarioHistorico historicoUsuario);

}
