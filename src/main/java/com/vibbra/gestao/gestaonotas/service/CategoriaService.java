package com.vibbra.gestao.gestaonotas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibbra.gestao.gestaonotas.domain.Categoria;
import com.vibbra.gestao.gestaonotas.exception.BusinessException;
import com.vibbra.gestao.gestaonotas.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional
	public Categoria salvarCategoria(Categoria obj) {
		categoriaRepository.save(obj);
		return obj;
	}

	public Categoria buscarId(Long id) {
		return categoriaRepository.findById(id)
				.orElseThrow(() -> new BusinessException("Categoria não encontrada"));
	}
	
	public List<Categoria> buscarNomes(String nome) {
		return categoriaRepository.findByNomeContainsIgnoreCase(nome);
	}
	
	public List<Categoria> buscarTodos() {
		return categoriaRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		categoriaRepository.deleteById(id);
	}
	
	@Transactional
	public Categoria alterar(Categoria obj, Long id){
		obj.setId(id);
		Categoria categoriaEditada = categoriaRepository.save(obj);
		return categoriaEditada;
	}
}
