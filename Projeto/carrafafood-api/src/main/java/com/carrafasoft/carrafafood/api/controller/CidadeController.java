package com.carrafasoft.carrafafood.api.controller;

import com.carrafasoft.carrafafood.api.assembler.CidadeInputDisassembler;
import com.carrafasoft.carrafafood.api.assembler.CidadeModelAssembler;
import com.carrafasoft.carrafafood.api.model.dto.CidadeModel;
import com.carrafasoft.carrafafood.api.model.input.CidadeInput;
import com.carrafasoft.carrafafood.domain.exception.CidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.exception.EstadoNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.exception.NegocioException;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import com.carrafasoft.carrafafood.domain.repository.CidadeRepository;
import com.carrafasoft.carrafafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	public static final String CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;



	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1")
								  @PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
									 @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cidadeService.salvar(cidade);

			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1")
								 @PathVariable Long cidadeId,
								 @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
								 @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cidadeService.salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Exclui uma cidade")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1")
							@PathVariable Long cidadeId) {

		try {
			cidadeService.excluir(cidadeId);
			cidadeRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(CIDADE_EM_USO, cidadeId));
		}
	}

}
