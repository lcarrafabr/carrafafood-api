package com.carrafasoft.carrafafood.api.v1.controller;

import java.util.List;

import com.carrafasoft.carrafafood.api.v1.assembler.CozinhaInputDisassembler;
import com.carrafasoft.carrafafood.api.v1.assembler.CozinhaModelAssembler;
import com.carrafasoft.carrafafood.api.v1.model.dto.CozinhaModel;
import com.carrafasoft.carrafafood.api.v1.model.input.CozinhaInput;
import com.carrafasoft.carrafafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.carrafasoft.carrafafood.domain.exception.CozinhaNaoEncontradaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.repository.CozinhaRepository;
import com.carrafasoft.carrafafood.domain.service.CadastroCozinhaService;

import javax.validation.Valid;

@Slf4j
@RestController
//@RequestMapping(value = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@RequestMapping(value = "/v1/cozinhas")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	private static final String COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;



	@PreAuthorize("isAuthenticated()")
	@Override
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		log.info("...: Consultado Cozinhas :...");


		PagedModel<CozinhaModel> cozinhasPagedmodel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);

		return cozinhasPagedmodel;

	}

//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public CozinhasXmlWrapper listarXml() {
//
//		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
//	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);

		log.info("...: Consultado Cozinhas :...");
		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cozinhaService.salvar(cozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
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

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {

		try {
			cozinhaService.excluir(cozinhaId);
			cozinhaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO, cozinhaId));
		}
	}
	
	@GetMapping("/listar-por-nome")
	public List<Cozinha> buscaPorNome(String nome) {
		
		return cozinhaRepository.findByNomeContaining(nome);
	}

}
