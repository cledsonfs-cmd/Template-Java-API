package br.com.template.model.dto;

import br.com.template.model.entity.Role;

public record UsuarioDTO(Integer id,
                         String email,
                         String nome,
                         String provedor,
                         String imageUrl,
                         Integer idstatus,
                         Role role) {
}
