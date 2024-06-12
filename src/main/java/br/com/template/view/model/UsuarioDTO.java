package br.com.template.view.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private Integer idStatus;
}
