package br.com.template.model.dto;

import br.com.template.enums.RoleName;

public record UpdateUserDto(
        Integer id,
        String email,
        String nome,
        String password,
        Integer idstatus,
        RoleName role) {
}
