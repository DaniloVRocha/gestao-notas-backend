package com.vibbra.gestao.gestaonotas.controller;

import java.net.URI;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vibbra.gestao.gestaonotas.domain.Empresa;
import com.vibbra.gestao.gestaonotas.repository.EmpresaRepository;
import com.vibbra.gestao.gestaonotas.service.EmpresaService;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaController {
	
	@Autowired
	EmpresaService empresaService;
	@Autowired
	EmpresaRepository empresaRepository;
	
	@GetMapping
	public ResponseEntity<List<Empresa>> buscarTodos(){
		List<Empresa> obj = empresaService.buscarTodos();
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Empresa> buscarId(@PathVariable Long id) throws ObjectNotFoundException {
		Empresa obj = empresaService.buscarId(id);
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value="/buscaNome")
	public ResponseEntity<List<Empresa>> buscarNome(@RequestParam("nome") String nome) throws ObjectNotFoundException {
		List<Empresa> obj = empresaService.buscarNomes(nome);
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value="/buscaCnpj")
	public ResponseEntity<List<Empresa>> buscarCnpj(@RequestParam("cnpj") String cnpj) throws ObjectNotFoundException {
		List<Empresa> obj = empresaService.buscarCnpj(cnpj);
		return ResponseEntity.status(200).body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Empresa obj){
		obj = empresaService.salvarEmpresa(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if(!empresaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		empresaService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Empresa> alterar(@PathVariable Long id, @RequestBody Empresa empresa) {
		if(!empresaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Empresa obj = empresaService.alterar(empresa, id);
		return ResponseEntity.status(200).body(obj);
	}
	

}
