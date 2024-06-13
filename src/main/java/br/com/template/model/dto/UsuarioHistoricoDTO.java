package br.com.template.model.dto;

import java.time.LocalDateTime;

public record UsuarioHistoricoDTO(
        Integer id,
        String descricao,
        LocalDateTime dataRegistro
) {
}
