package com.carrafasoft.carrafafood.api.v2.controller;

import com.carrafasoft.carrafafood.api.ResourceUriHelper;
import com.carrafasoft.carrafafood.api.v1.assembler.CidadeInputDisassembler;
import com.carrafasoft.carrafafood.api.v1.assembler.CidadeModelAssembler;
import com.carrafasoft.carrafafood.api.v1.model.dto.CidadeModel;
import com.carrafasoft.carrafafood.api.v1.model.input.CidadeInput;
import com.carrafasoft.carrafafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.carrafasoft.carrafafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.carrafasoft.carrafafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.carrafasoft.carrafafood.api.v2.model.CidadeModelV2;
import com.carrafasoft.carrafafood.api.v2.model.input.CidadeInputV2;
import com.carrafasoft.carrafafood.api.v2.openapiv2.controller.CidadeControllerV2OpenApi;
import com.carrafasoft.carrafafood.core.web.AlgaMediaTypes;
import com.carrafasoft.carrafafood.domain.exception.CidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.exception.EstadoNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.exception.NegocioException;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import com.carrafasoft.carrafafood.domain.repository.CidadeRepository;
import com.carrafasoft.carrafafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(path = "/cidades", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

	public static final String CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CidadeModelAssemblerV2 cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;



	@GetMapping
	public CollectionModel<CidadeModelV2> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(todasCidades);

		//cidadesCollectionModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}

	@GetMapping("/{cidadeId}")
	public CidadeModelV2 buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

		CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);

		return cidadeModel;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cidadeService.salvar(cidade);

			CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);

			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());

			return cidadeModel;
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeModelV2 atualizar(@PathVariable Long cidadeId,
								 @RequestBody @Valid CidadeInputV2 cidadeInput) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cidadeService.salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

  //Não pode ser mapeado na mesma URL em um MediaType diferente, já que não aceita entrada e retorna void.
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {

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
