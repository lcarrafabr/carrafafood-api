package com.carrafasoft.carrafafood.api.controller;

import java.util.List;
import java.util.Optional;

import com.carrafasoft.carrafafood.api.assembler.CozinhaInputDisassembler;
import com.carrafasoft.carrafafood.api.assembler.CozinhaModelAssembler;
import com.carrafasoft.carrafafood.api.model.dto.CozinhaModel;
import com.carrafasoft.carrafafood.api.model.input.CozinhaInput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.carrafafood.api.model.CozinhasXmlWrapper;
import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.repository.CozinhaRepository;
import com.carrafasoft.carrafafood.domain.service.CadastroCozinhaService;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
//@RequestMapping(value = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;



	@GetMapping
	public List<CozinhaModel> listar() {
		List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

		return cozinhaModelAssembler.toCollectionModel(todasCozinhas);
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {

		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cozinhaService.salvar(cozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId,
								  @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);

		return cozinhaModelAssembler.toModel(cozinhaAtual);
	}

//	@DeleteMapping("/{cozinhaId}")
//	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
//
//		try {
//			cozinhaService.excluir(cozinhaId);
//			return ResponseEntity.noContent().build();
//
//		} catch (EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {

		cozinhaService.excluir(cozinhaId);
	}
	
	@GetMapping("/listar-por-nome")
	public List<Cozinha> buscaPorNome(String nome) {
		
		return cozinhaRepository.findByNomeContaining(nome);
	}

}
