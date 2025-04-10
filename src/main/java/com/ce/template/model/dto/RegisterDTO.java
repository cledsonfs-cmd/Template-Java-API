package com.ce.template.model.dto;

import com.ce.template.model.enums.UserRoleEnum;

public record RegisterDTO (String login, String password, UserRoleEnum role){
}
