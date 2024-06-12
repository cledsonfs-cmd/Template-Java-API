package br.com.template.repository;

import br.com.template.model.UsuarioHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoUsuarioRepository extends JpaRepository<UsuarioHistorico, Long> {
}
