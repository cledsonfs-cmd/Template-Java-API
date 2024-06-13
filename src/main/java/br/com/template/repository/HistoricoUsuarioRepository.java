package br.com.template.repository;

import br.com.template.model.entity.UsuarioHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoUsuarioRepository extends JpaRepository<UsuarioHistorico, Long> {
}
