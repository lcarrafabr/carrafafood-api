package com.carrafasoft.carrafafood.api.v1.controller;

import java.util.List;

import com.carrafasoft.carrafafood.api.v1.assembler.EstadoInputDisassembler;
import com.carrafasoft.carrafafood.api.v1.assembler.EstadoModelAssembler;
import com.carrafasoft.carrafafood.api.v1.model.dto.EstadoModel;
import com.carrafasoft.carrafafood.api.v1.model.input.EstadoInput;
import com.carrafasoft.carrafafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.carrafasoft.carrafafood.domain.exception.EstadoNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.carrafasoft.carrafafood.domain.model.Estado;
import com.carrafasoft.carrafafood.domain.repository.EstadoRepository;
import com.carrafasoft.carrafafood.domain.service.CadastroEstadoService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	public static final String ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService estadoService;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;



	@GetMapping
	public CollectionModel<EstadoModel> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();

		return estadoModelAssembler.toCollectionModel(todosEstados);
	}

	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		Estado estado = estadoService.buscarOuFalhar(estadoId);

		return estadoModelAssembler.toModel(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

		estado = estadoService.salvar(estado);

		return estadoModelAssembler.toModel(estado);
	}

	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId,
								 @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		estadoAtual = estadoService.salvar(estadoAtual);

		return estadoModelAssembler.toModel(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {

		try {
			estadoService.excluir(estadoId);
			estadoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO , estadoId));
		}
	}

}
