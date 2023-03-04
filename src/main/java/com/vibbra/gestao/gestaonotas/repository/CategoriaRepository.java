package com.vibbra.gestao.gestaonotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.vibbra.gestao.gestaonotas.domain.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{

	@Transactional(readOnly = true)
	List<Categoria> findByNomeContainsIgnoreCase(String nome);
}
