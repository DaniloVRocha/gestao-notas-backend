package com.vibbra.gestao.gestaonotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vibbra.gestao.gestaonotas.domain.Faturamento;

public interface FaturamentoRepository  extends JpaRepository<Faturamento, Integer>{
	
}
