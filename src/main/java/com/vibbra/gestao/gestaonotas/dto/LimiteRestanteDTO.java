package com.vibbra.gestao.gestaonotas.dto;

import java.math.BigDecimal;

public class LimiteRestanteDTO {

	private BigDecimal limite;
	private BigDecimal valorRestante;
	private BigDecimal valorUtilizado;

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public BigDecimal getValorRestante() {
		return valorRestante;
	}

	public void setValorRestante(BigDecimal valorRestante) {
		this.valorRestante = valorRestante;
	}

	public BigDecimal getValorUtilizado() {
		return valorUtilizado;
	}

	public void setValorUtilizado(BigDecimal valorUtilizado) {
		this.valorUtilizado = valorUtilizado;
	}
}
