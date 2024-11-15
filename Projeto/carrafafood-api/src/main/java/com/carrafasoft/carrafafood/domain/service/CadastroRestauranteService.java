package com.carrafasoft.carrafafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.model.Restaurante;
import com.carrafasoft.carrafafood.domain.repository.CozinhaRepository;
import com.carrafasoft.carrafafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired 
	private CozinhaRepository cozinhaRepository;
	
	public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe um cadastro com o código %d";
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		if(cozinha == null) {
			throw new EntidadeNaoEncontradaException(String.format(NAO_EXISTE_CADASTRO_COM_ID, cozinhaId));
		}
		
		restaurante.setCozinha(cozinha.get());
		
		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(NAO_EXISTE_CADASTRO_COM_ID, restauranteId)));
	}

}
