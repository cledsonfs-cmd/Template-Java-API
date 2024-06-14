package br.com.template.repository;

import br.com.template.model.entity.Usuario;
import br.com.template.model.entity.UsuarioHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoUsuarioRepository extends JpaRepository<UsuarioHistorico, Integer> {

    List<UsuarioHistorico> findByUsuario(Usuario usuario);
}
