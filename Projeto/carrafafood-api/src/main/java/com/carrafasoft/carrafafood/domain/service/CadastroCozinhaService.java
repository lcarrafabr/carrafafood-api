package com.carrafasoft.carrafafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.repository.CozinhaRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	private static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	private static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe um cadastro com o código %d";

	public Cozinha salvar(Cozinha cozinha) {

		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {

		try {
			cozinhaRepository.deleteById(cozinhaId);
		} catch (EmptyResultDataAccessException e) {
			//throw new EntidadeNaoEncontradaException(String.format(NAO_EXISTE_CADASTRO_COM_ID, cozinhaId));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(NAO_EXISTE_CADASTRO_COM_ID, cozinhaId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha buscarOuFalhar(Long cozinhaId) {

		return cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(NAO_EXISTE_CADASTRO_COM_ID, cozinhaId)));
	}

}
