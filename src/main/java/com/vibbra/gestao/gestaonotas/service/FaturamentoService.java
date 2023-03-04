package com.vibbra.gestao.gestaonotas.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibbra.gestao.gestaonotas.domain.Despesa;
import com.vibbra.gestao.gestaonotas.domain.Faturamento;
import com.vibbra.gestao.gestaonotas.domain.NotaFiscal;
import com.vibbra.gestao.gestaonotas.dto.AlterarLimiteDTO;
import com.vibbra.gestao.gestaonotas.dto.LimiteRestanteDTO;
import com.vibbra.gestao.gestaonotas.exception.BusinessException;
import com.vibbra.gestao.gestaonotas.repository.FaturamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class FaturamentoService {	
	
	@Autowired
	private FaturamentoRepository faturamentoRepository;
	
	public Faturamento buscarId(Integer anoExercicio) {
		return faturamentoRepository.findById(anoExercicio).orElseThrow(() -> new BusinessException("Faturamento não encontrado para ano selecionado."));
	}
	
	@Transactional
	public Faturamento criarFaturamento(Faturamento obj) {
		if(!faturamentoRepository.existsById(obj.getAnoExercicio())) {
			faturamentoRepository.save(obj);
			return obj;
		}else {
			return null;
		}
	}
	
	@Transactional
	public Faturamento adicionarValorEntrada(Integer anoExercicio, NotaFiscal nota) {
		Faturamento faturamento = buscarId(anoExercicio);
		BigDecimal valorEntrada = faturamento.getValorTotalEntrada();
		BigDecimal valorNota = nota.getValor();
		faturamento.setValorTotalEntrada(valorEntrada.add(valorNota));

		Faturamento novoFaturamento = faturamentoRepository.save(faturamento);
		return novoFaturamento;
	}
	
	@Transactional
	public Faturamento adicionarValorSaida(Integer anoExercicio, Despesa despesa) {
		Faturamento faturamento = buscarId(anoExercicio);
		BigDecimal valorSaida = faturamento.getValorTotalSaida();
		BigDecimal valorDespesa = despesa.getValor();
		faturamento.setValorTotalSaida(valorSaida.add(valorDespesa));

		Faturamento novoFaturamento = faturamentoRepository.save(faturamento);
		return novoFaturamento;
	}
	
	@Transactional
	public Faturamento removeValorEntrada(Integer anoExercicio, NotaFiscal nota) {
		Faturamento faturamento = buscarId(anoExercicio);
		BigDecimal valorEntrada = faturamento.getValorTotalEntrada();
		BigDecimal valorNota = nota.getValor();
		faturamento.setValorTotalEntrada(valorEntrada.subtract(valorNota));

		Faturamento novoFaturamento = faturamentoRepository.save(faturamento);
		return novoFaturamento;
	}
	
	@Transactional
	public Faturamento removeValorSaida(Integer anoExercicio, Despesa despesa) {
		Faturamento faturamento = buscarId(anoExercicio);
		BigDecimal valorSaida = faturamento.getValorTotalSaida();
		BigDecimal valorDespesa = despesa.getValor();
		faturamento.setValorTotalSaida(valorSaida.subtract(valorDespesa));

		Faturamento novoFaturamento = faturamentoRepository.save(faturamento);
		return novoFaturamento;
	}
	
	@Transactional
	public Faturamento alterarLimite(AlterarLimiteDTO dto) {
		Faturamento faturamento = buscarId(dto.getAnoExercicio());
		faturamento.setLimiteMaximo(dto.getLimite());
		
		Faturamento novoFaturamento = faturamentoRepository.save(faturamento);
		return novoFaturamento;
	}
	
	public LimiteRestanteDTO limiteAno(Integer anoExercicio) {
		Faturamento faturamento = buscarId(anoExercicio);
		BigDecimal limiteAno = new BigDecimal(faturamento.getLimiteMaximo());
		BigDecimal valorAtual = faturamento.getValorTotalEntrada();
		LimiteRestanteDTO resp = new LimiteRestanteDTO();
		
		resp.setLimite(limiteAno);
		resp.setValorRestante(limiteAno.subtract(valorAtual));
		resp.setValorUtilizado(valorAtual);

		return resp;
	}
	
}
