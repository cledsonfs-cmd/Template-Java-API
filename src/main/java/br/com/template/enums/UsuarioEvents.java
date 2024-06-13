package br.com.template.enums;

import br.com.template.model.dto.UsuarioHistoricoDTO;
import br.com.template.model.entity.Usuario;
import br.com.template.model.entity.UsuarioHistorico;
import lombok.Getter;
import lombok.Setter;

public enum UsuarioEvents {

    INICIAR,
    ATIVAR,
    SUSPENDER,
    INATIVAR,
    EXCLUIR;

    @Getter @Setter
    private Usuario usuario;

    @Getter @Setter
    private UsuarioHistorico usuarioHistorico;
}
