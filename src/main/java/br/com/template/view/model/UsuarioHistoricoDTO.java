package br.com.template.view.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioHistoricoDTO {

    private Long id;
    private String descricao;
    private LocalDate dataRegistro;

}
