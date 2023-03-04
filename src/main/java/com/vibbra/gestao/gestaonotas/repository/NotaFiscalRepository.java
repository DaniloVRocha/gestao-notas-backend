package com.vibbra.gestao.gestaonotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vibbra.gestao.gestaonotas.domain.NotaFiscal;

public interface NotaFiscalRepository  extends JpaRepository<NotaFiscal, Long>{
	
	List<NotaFiscal> findByMesCompetencia(Integer mesCompetencia);
}
