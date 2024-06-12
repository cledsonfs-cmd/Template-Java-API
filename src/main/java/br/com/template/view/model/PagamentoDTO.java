package br.com.template.view.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {

    private Long idPagamento;
    private int anoPagamento;
    private String numeroPagamento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataPagamento;
    private float valorPagamento;
    private String observacao;
    private long codigoEmpenho;    
    private long codigoDespesa;    
    private String codigoProtocolo;    
}
