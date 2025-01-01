package com.carrafasoft.carrafafood.domain.service;

import com.carrafasoft.carrafafood.domain.exception.RestauranteNaoEncontradoException;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.model.Restaurante;
import com.carrafasoft.carrafafood.domain.repository.CozinhaRepository;
import com.carrafasoft.carrafafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired 
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private  CadastroCidadeService cadastroCidadeService;
	
	public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe um cadastro com o código %d";

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar(Long restauranteId) {

		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
	}

	@Transactional
	public void inativar(Long restauranteId) {

		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.inativar();
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

}
