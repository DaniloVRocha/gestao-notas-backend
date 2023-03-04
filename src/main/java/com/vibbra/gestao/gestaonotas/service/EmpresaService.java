package com.vibbra.gestao.gestaonotas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibbra.gestao.gestaonotas.domain.Empresa;
import com.vibbra.gestao.gestaonotas.exception.BusinessException;
import com.vibbra.gestao.gestaonotas.repository.EmpresaRepository;

import jakarta.transaction.Transactional;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Transactional
	public Empresa salvarEmpresa(Empresa obj) {
		empresaRepository.save(obj);
		return obj;
	}

	public Empresa buscarId(Long id) {
		return empresaRepository.findById(id)
				.orElseThrow(() -> new BusinessException("Empresa não encontrada"));
	}
	
	public List<Empresa> buscarNomes(String nome) {
		return empresaRepository.findByNomeContainsIgnoreCase(nome);
	}
	
	public List<Empresa> buscarCnpj(String cnpj) {
		return empresaRepository.findByCnpjContains(cnpj);
	}
	
	public List<Empresa> buscarTodos() {
		return empresaRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		empresaRepository.deleteById(id);
	}
	
	@Transactional
	public Empresa alterar(Empresa obj, Long id){
		obj.setId(id);
		Empresa empresaEditada = empresaRepository.save(obj);
		return empresaEditada;
	}
}
