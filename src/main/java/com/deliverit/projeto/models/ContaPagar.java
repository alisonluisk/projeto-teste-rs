package com.deliverit.projeto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaPagar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal valor;

    private Date dataVencimento;

    private Date dataPagamento;

    private Integer diasAtraso = 0;

    private BigDecimal multa = BigDecimal.ZERO;

    private BigDecimal jurosDia = BigDecimal.ZERO;

    private BigDecimal valorCorrigido;

}
