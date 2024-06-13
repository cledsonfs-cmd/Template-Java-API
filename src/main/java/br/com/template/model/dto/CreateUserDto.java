package br.com.template.model.dto;

import br.com.template.enums.RoleName;

public record CreateUserDto(
        String email,
        String nome,
        String password,
        Integer idstatus,
        RoleName role) {
}
