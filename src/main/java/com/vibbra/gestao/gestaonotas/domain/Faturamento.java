package com.vibbra.gestao.gestaonotas.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Faturamento {
	
	@Id
	private Integer anoExercicio;
	private Long limiteMaximo;
	private BigDecimal valorTotalEntrada;
	private BigDecimal valorTotalSaida;
	private boolean alerta;

	
	public Faturamento() {
		super();
	}

	public Faturamento(Integer anoExercicio, Long limiteMaximo, BigDecimal valorTotalEntrada,
			BigDecimal valorTotalSaida, boolean alerta) {
		super();
		this.anoExercicio = anoExercicio;
		this.limiteMaximo = limiteMaximo;
		this.valorTotalEntrada = valorTotalEntrada;
		this.valorTotalSaida = valorTotalSaida;
		this.alerta = alerta;
	}

	public Long getLimiteMaximo() {
		return limiteMaximo;
	}

	public void setLimiteMaximo(Long limiteMaximo) {
		this.limiteMaximo = limiteMaximo;
	}

	public Integer getAnoExercicio() {
		return anoExercicio;
	}

	public void setAnoExercicio(Integer anoExercicio) {
		this.anoExercicio = anoExercicio;
	}

	public BigDecimal getValorTotalEntrada() {
		return valorTotalEntrada;
	}

	public void setValorTotalEntrada(BigDecimal valorTotalEntrada) {
		this.valorTotalEntrada = valorTotalEntrada;
	}

	public BigDecimal getValorTotalSaida() {
		return valorTotalSaida;
	}

	public void setValorTotalSaida(BigDecimal valorTotalSaida) {
		this.valorTotalSaida = valorTotalSaida;
	}

	public boolean isAlerta() {
		return alerta;
	}

	public void setAlerta(boolean alerta) {
		this.alerta = alerta;
	}

}
