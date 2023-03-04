package com.vibbra.gestao.gestaonotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vibbra.gestao.gestaonotas.domain.Categoria;
import com.vibbra.gestao.gestaonotas.domain.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
	List<Despesa> findByCategoria(Categoria categoria);
}
