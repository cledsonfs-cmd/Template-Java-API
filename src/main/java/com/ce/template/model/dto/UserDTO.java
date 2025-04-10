package com.ce.template.model.dto;

import com.ce.template.model.enums.UserRoleEnum;

public record UserDTO(Integer id,
                      String login,
                      UserRoleEnum role,
                      boolean ativo) {
}
