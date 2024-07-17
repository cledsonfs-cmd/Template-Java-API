package br.com.template.model.dto;

import java.time.LocalDateTime;

public record ErrorDTO (
        int status,
        String error,
        LocalDateTime timestamp){
}
