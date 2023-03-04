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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vibbra.gestao.gestaonotas.domain.Despesa;
import com.vibbra.gestao.gestaonotas.repository.DespesaRepository;
import com.vibbra.gestao.gestaonotas.service.DespesaService;

@RestController
@RequestMapping(value = "/despesas")
public class DespesaController {
	
	@Autowired
	DespesaService despesaService;
	@Autowired
	DespesaRepository despesaRepository;
	
	@GetMapping
	public ResponseEntity<List<Despesa>> buscarTodos(){
		List<Despesa> obj = despesaService.buscarTodos();
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Despesa> buscarId(@PathVariable Long id) throws ObjectNotFoundException {
		Despesa obj = despesaService.buscarId(id);
		return ResponseEntity.status(200).body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Despesa obj){
		obj = despesaService.salvarDespesa(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if(!despesaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		despesaService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Despesa> alterar(@PathVariable Long id, @RequestBody Despesa despesa) {
		if(!despesaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Despesa obj = despesaService.alterar(despesa, id);
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value = "/consultar-por-categoria/{idCategoria}")
	public ResponseEntity<List<Despesa>> buscarPorCategoria(@PathVariable Long idCategoria) throws ObjectNotFoundException {
		List<Despesa> despesas = despesaService.buscarPorCategorias(idCategoria);
		return ResponseEntity.status(200).body(despesas);
	}

}
