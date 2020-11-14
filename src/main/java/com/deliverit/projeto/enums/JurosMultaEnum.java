package com.deliverit.projeto.enums;

import java.math.BigDecimal;

public enum JurosMultaEnum {

	REGRA_1(BigDecimal.valueOf(2), new BigDecimal("0.10")),
	REGRA_2(BigDecimal.valueOf(3), new BigDecimal("0.20")),
	REGRA_3(BigDecimal.valueOf(5), new BigDecimal("0.30"));

	private BigDecimal multa;
	private BigDecimal jurosDias;

	private JurosMultaEnum(BigDecimal multa, BigDecimal jurosDias) {
		this.jurosDias = jurosDias;
		this.multa = multa;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public BigDecimal getJurosDias() {
		return jurosDias;
	}

	public static JurosMultaEnum getRegraPorDiasAtraso(Long diasAtraso){
		if(diasAtraso <= 3)
			return REGRA_1;
		if(diasAtraso <= 5)
			return REGRA_2;

		return REGRA_3;
	}
}
