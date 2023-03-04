package com.vibbra.gestao.gestaonotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.vibbra.gestao.gestaonotas.domain.Empresa;

public interface EmpresaRepository  extends JpaRepository<Empresa, Long>{
	
	@Transactional(readOnly = true)
	List<Empresa> findByNomeContainsIgnoreCase(String nome);
	
	@Transactional(readOnly = true)
	List<Empresa> findByCnpjContains(String cnpj);
}
