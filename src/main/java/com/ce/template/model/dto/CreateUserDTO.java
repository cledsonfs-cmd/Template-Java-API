package com.ce.template.model.dto;

public record CreateUserDTO(String login,
                            String password,
                            String role) {
}
