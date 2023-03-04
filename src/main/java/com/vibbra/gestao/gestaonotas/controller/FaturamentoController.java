package com.vibbra.gestao.gestaonotas.controller;

import java.net.URI;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vibbra.gestao.gestaonotas.domain.Faturamento;
import com.vibbra.gestao.gestaonotas.dto.AlterarLimiteDTO;
import com.vibbra.gestao.gestaonotas.dto.LimiteRestanteDTO;
import com.vibbra.gestao.gestaonotas.service.FaturamentoService;

@RestController
@RequestMapping(value = "/faturamento")
public class FaturamentoController {
	
	@Autowired
	FaturamentoService faturamentoService;
	
	@GetMapping(value = "/{anoExercicio}")
	public ResponseEntity<Faturamento> buscarId(@PathVariable Integer anoExercicio) throws ObjectNotFoundException {
		Faturamento obj = faturamentoService.buscarId(anoExercicio);
		return ResponseEntity.status(200).body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Faturamento obj){
		obj = faturamentoService.criarFaturamento(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getAnoExercicio()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/alterar-limite")
	public ResponseEntity<Faturamento> alterarValorMEI(@RequestBody AlterarLimiteDTO obj){
		Faturamento faturamento = faturamentoService.alterarLimite(obj);
		return ResponseEntity.status(200).body(faturamento);
	}
	
	@GetMapping("/consultar-limite/{anoExercicio}")
	public ResponseEntity<LimiteRestanteDTO> consultarLimite(@PathVariable Integer anoExercicio){
		LimiteRestanteDTO limiteRestante = faturamentoService.limiteAno(anoExercicio);
		return ResponseEntity.status(200).body(limiteRestante);
	}
	
}
