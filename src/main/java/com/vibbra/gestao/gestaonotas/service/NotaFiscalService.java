package com.vibbra.gestao.gestaonotas.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibbra.gestao.gestaonotas.domain.Faturamento;
import com.vibbra.gestao.gestaonotas.domain.NotaFiscal;
import com.vibbra.gestao.gestaonotas.exception.BusinessException;
import com.vibbra.gestao.gestaonotas.repository.FaturamentoRepository;
import com.vibbra.gestao.gestaonotas.repository.NotaFiscalRepository;

import jakarta.transaction.Transactional;

@Service
public class NotaFiscalService {

	@Autowired
	private NotaFiscalRepository notaRepository;
	@Autowired
	private FaturamentoService faturamentoService;
	@Autowired
	private FaturamentoRepository faturamentoRepository;

	@Transactional
	public NotaFiscal salvarNota(NotaFiscal obj) {
		if (faturamentoRepository.existsById(obj.getDataRecebimento().getYear())) {
			faturamentoService.adicionarValorEntrada(obj.getDataRecebimento().getYear(), obj);
		} else {
			Faturamento faturamentoZerado = new Faturamento(obj.getDataRecebimento().getYear(), 81000L, obj.getValor(),
					new BigDecimal(0.0), false);
			faturamentoService.criarFaturamento(faturamentoZerado);
		}

		notaRepository.save(obj);
		return obj;
	}

	public NotaFiscal buscarId(Long id) {
		return notaRepository.findById(id).orElseThrow(() -> new BusinessException("Nota Fiscal não encontrada"));
	}

	public List<NotaFiscal> buscarTodos() {
		return notaRepository.findAll();
	}

	@Transactional
	public void excluir(Long id) {
		NotaFiscal nota = buscarId(id);
		faturamentoService.removeValorEntrada(nota.getDataRecebimento().getYear(), nota);
		notaRepository.deleteById(id);
	}

	@Transactional
	public NotaFiscal alterar(NotaFiscal obj, Long id) {
		NotaFiscal notaAntiga = buscarId(id);

		if (notaAntiga.getDataRecebimento().getYear() != obj.getDataRecebimento().getYear()) {
			faturamentoService.removeValorEntrada(notaAntiga.getDataRecebimento().getYear(), obj);
			if (faturamentoRepository.existsById(obj.getDataRecebimento().getYear())) {
				faturamentoService.adicionarValorEntrada(obj.getDataRecebimento().getYear(), obj);
			} else {
				Faturamento faturamentoZerado = new Faturamento(obj.getDataRecebimento().getYear(), 81000L,
						obj.getValor(), new BigDecimal(0.0), false);
				faturamentoService.criarFaturamento(faturamentoZerado);
			}
		}

		obj.setId(id);
		NotaFiscal notaEditada = notaRepository.save(obj);
		return notaEditada;
	}
	
	public List<NotaFiscal> buscarMes(Integer mesCompetencia) {
		return notaRepository.findByMesCompetencia(mesCompetencia);
	}
}
