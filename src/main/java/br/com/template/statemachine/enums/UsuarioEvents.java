package br.com.template.statemachine.enums;

import br.com.template.model.Usuario;
import br.com.template.view.model.UsuarioHistoricoDTO;
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
    private UsuarioHistoricoDTO usuarioHistoricoDTO;
}
