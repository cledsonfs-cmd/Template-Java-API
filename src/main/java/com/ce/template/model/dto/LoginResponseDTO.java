package com.ce.template.model.dto;

import com.ce.template.model.enums.UserRoleEnum;

public record LoginResponseDTO(Integer id,
                               String login,
                               UserRoleEnum role,
                               String token) {
}
