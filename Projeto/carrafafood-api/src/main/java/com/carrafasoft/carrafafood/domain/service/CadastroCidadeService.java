package com.carrafasoft.carrafafood.domain.service;

import com.carrafasoft.carrafafood.domain.exception.CidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import com.carrafasoft.carrafafood.domain.repository.CidadeRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;
	
	public static final String CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";
	public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe um cadastro com o código %d";

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

//		Estado estado = estadoRepository.findById(estadoId)
//			.orElseThrow(() -> new EntidadeNaoEncontradaException(
//					String.format("Não existe cadastro de estado com código %d", estadoId)));

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
	public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(CIDADE_EM_USO, cidadeId));
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

}
