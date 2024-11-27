package com.carrafasoft.carrafafood.domain.service;

import com.carrafasoft.carrafafood.domain.exception.EstadoNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Estado;
import com.carrafasoft.carrafafood.domain.repository.EstadoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public static final String ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso.";
	public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe um cadastro com o código %d";

    @Transactional
	public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public void excluir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
            
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradaException(estadoId);
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(ESTADO_EM_USO, estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradaException(estadoId));
    }

}
