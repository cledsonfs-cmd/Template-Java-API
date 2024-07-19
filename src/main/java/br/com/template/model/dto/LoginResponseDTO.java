package br.com.template.model.dto;

public record LoginResponseDTO(
        Integer uid,
        String email,
        String nome,
        RecoveryJwtTokenDto token,
        String provedor,
        String imageUrl,
        String role
) {
}
