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

import com.vibbra.gestao.gestaonotas.domain.Categoria;
import com.vibbra.gestao.gestaonotas.repository.CategoriaRepository;
import com.vibbra.gestao.gestaonotas.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> buscarTodos(){
		List<Categoria> obj = categoriaService.buscarTodos();
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscarId(@PathVariable Long id) throws ObjectNotFoundException {
		Categoria obj = categoriaService.buscarId(id);
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value="/buscaNome")
	public ResponseEntity<List<Categoria>> buscarNome(@RequestParam("nome") String nome) throws ObjectNotFoundException {
		List<Categoria> obj = categoriaService.buscarNomes(nome);
		return ResponseEntity.status(200).body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = categoriaService.salvarCategoria(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if(!categoriaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		categoriaService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Categoria> alterar(@PathVariable Long id, @RequestBody Categoria categoria) {
		if(!categoriaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Categoria obj = categoriaService.alterar(categoria, id);
		return ResponseEntity.status(200).body(obj);
	}
	

}
