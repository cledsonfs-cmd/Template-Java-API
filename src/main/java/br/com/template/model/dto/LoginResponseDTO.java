package br.com.template.model.dto;

import br.com.template.model.entity.Role;

public record LoginResponseDTO(
        Integer uuid,
        String email,
        String nome,
        RecoveryJwtTokenDto token,
        String provedor,
        String imageUrl,
        Role role
) {
}
