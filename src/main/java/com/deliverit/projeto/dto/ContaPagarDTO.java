package com.deliverit.projeto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
public class ContaPagarDTO {

    private Long id;

    @NotNull(message = "Nome deve ser informado")
    private String nome;

    @NotNull(message = "Valor deve ser informado")
    private BigDecimal valor;

    @NotNull(message = "Data de vencimento deve ser informada")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Sao_Paulo")
    private Date dataVencimento;

    @NotNull(message = "Data de pagamento deve ser informada")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="America/Sao_Paulo")
    private Date dataPagamento;

    private BigDecimal valorCorrigido;
}
