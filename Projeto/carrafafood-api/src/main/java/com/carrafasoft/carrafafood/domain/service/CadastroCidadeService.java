package com.carrafasoft.carrafafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import com.carrafasoft.carrafafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public static final String CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";
	public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe um cadastro com o código %d";
	
	public Cidade salvar(Cidade cidade) {
		
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format(NAO_EXISTE_CADASTRO_COM_ID, cidadeId));
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(CIDADE_EM_USO, cidadeId));
        }
    } 

}
