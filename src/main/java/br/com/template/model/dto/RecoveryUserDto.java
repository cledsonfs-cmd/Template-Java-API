package br.com.template.model.dto;

import br.com.template.model.entity.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        Integer idstatus,
        List<Role> roles) {
}
