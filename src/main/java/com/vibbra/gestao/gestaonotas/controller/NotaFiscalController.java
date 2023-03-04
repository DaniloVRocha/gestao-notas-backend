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

import com.vibbra.gestao.gestaonotas.domain.NotaFiscal;
import com.vibbra.gestao.gestaonotas.repository.NotaFiscalRepository;
import com.vibbra.gestao.gestaonotas.service.NotaFiscalService;

@RestController
@RequestMapping(value = "/notas")
public class NotaFiscalController {
	
	@Autowired
	NotaFiscalService notaService;
	@Autowired
	NotaFiscalRepository notaRepository;
	
	@GetMapping
	public ResponseEntity<List<NotaFiscal>> buscarTodos(){
		List<NotaFiscal> obj = notaService.buscarTodos();
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NotaFiscal> buscarId(@PathVariable Long id) throws ObjectNotFoundException {
		NotaFiscal obj = notaService.buscarId(id);
		return ResponseEntity.status(200).body(obj);
	}
	
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody NotaFiscal obj){
		obj = notaService.salvarNota(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if(!notaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		notaService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<NotaFiscal> alterar(@PathVariable Long id, @RequestBody NotaFiscal nota) {
		if(!notaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		NotaFiscal obj = notaService.alterar(nota, id);
		return ResponseEntity.status(200).body(obj);
	}
	
	@GetMapping("/consulta-por-mes/{mesCompetencia}")
	public ResponseEntity<List<NotaFiscal>> buscarMes(@PathVariable Integer mesCompetencia){
		List<NotaFiscal> obj = notaService.buscarMes(mesCompetencia);
		return ResponseEntity.status(200).body(obj);
	}
	

}
