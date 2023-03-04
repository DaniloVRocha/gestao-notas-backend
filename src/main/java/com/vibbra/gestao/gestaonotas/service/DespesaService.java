package com.vibbra.gestao.gestaonotas.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibbra.gestao.gestaonotas.domain.Categoria;
import com.vibbra.gestao.gestaonotas.domain.Despesa;
import com.vibbra.gestao.gestaonotas.domain.Faturamento;
import com.vibbra.gestao.gestaonotas.exception.BusinessException;
import com.vibbra.gestao.gestaonotas.repository.DespesaRepository;
import com.vibbra.gestao.gestaonotas.repository.FaturamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;
	@Autowired
	private FaturamentoService faturamentoService;
	@Autowired
	private FaturamentoRepository faturamentoRepository;
	@Autowired
	private CategoriaService categoriaRepository;
	
	@Transactional
	public Despesa salvarDespesa(Despesa obj) {
		if (faturamentoRepository.existsById(obj.getDataPagamento().getYear())) {
			faturamentoService.adicionarValorSaida(obj.getDataPagamento().getYear(), obj);
		} else {
			Faturamento faturamentoZerado = new Faturamento(obj.getDataPagamento().getYear(), 81000L, new BigDecimal(0.0),obj.getValor(),
					 false);
			faturamentoService.criarFaturamento(faturamentoZerado);
		}

		despesaRepository.save(obj);
		return obj;
	}

	public Despesa buscarId(Long id) {
		return despesaRepository.findById(id)
				.orElseThrow(() -> new BusinessException("Despesa não encontrada"));
	}
	
	public List<Despesa> buscarTodos() {
		return despesaRepository.findAll();
	}
	
	@Transactional
	public void excluir(Long id) {
		Despesa despesa = buscarId(id);
		faturamentoService.removeValorSaida(despesa.getDataPagamento().getYear(), despesa);
		despesaRepository.deleteById(id);
	}
	
	@Transactional
	public Despesa alterar(Despesa obj, Long id){
		
		Despesa despesaAntiga = buscarId(id);

		if (despesaAntiga.getDataPagamento().getYear() != obj.getDataPagamento().getYear()) {
			faturamentoService.removeValorSaida(despesaAntiga.getDataPagamento().getYear(), despesaAntiga);
			if (faturamentoRepository.existsById(obj.getDataPagamento().getYear())) {
				faturamentoService.adicionarValorSaida(obj.getDataPagamento().getYear(), obj);
			} else {
				Faturamento faturamentoZerado = new Faturamento(obj.getDataPagamento().getYear(), 81000L,
						new BigDecimal(0.0), obj.getValor(), false);
				faturamentoService.criarFaturamento(faturamentoZerado);
			}
		}
		
		obj.setId(id);
		Despesa despesaEditada = despesaRepository.save(obj);
		return despesaEditada;
	}
	
	public List<Despesa> buscarPorCategorias(Long idCategoria){
		Categoria categoria = categoriaRepository.buscarId(idCategoria);
		List<Despesa> despesas = despesaRepository.findByCategoria(categoria);
		return despesas;
	}

}
