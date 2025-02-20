package com.carrafasoft.carrafafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.carrafasoft.carrafafood.api.assembler.RestauranteApenasNomeModelAssembler;
import com.carrafasoft.carrafafood.api.assembler.RestauranteBasicoModelAssembler;
import com.carrafasoft.carrafafood.api.assembler.RestauranteInputDisassembler;
import com.carrafasoft.carrafafood.api.assembler.RestauranteModelAssembler;
import com.carrafasoft.carrafafood.api.model.dto.RestauranteApenasNomeModel;
import com.carrafasoft.carrafafood.api.model.dto.RestauranteModel;
import com.carrafasoft.carrafafood.api.model.input.RestauranteInput;
import com.carrafasoft.carrafafood.api.model.view.RestauranteView;
import com.carrafasoft.carrafafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.carrafasoft.carrafafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.carrafasoft.carrafafood.domain.exception.CidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.exception.CozinhaNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.exception.NegocioException;
import com.carrafasoft.carrafafood.domain.exception.RestauranteNaoEncontradoException;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import com.carrafasoft.carrafafood.domain.model.Restaurante;
import com.carrafasoft.carrafafood.domain.repository.RestauranteRepository;
import com.carrafasoft.carrafafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class
RestauranteController implements RestauranteControllerOpenApi {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private SmartValidator validator;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;



	@GetMapping
	public CollectionModel<RestauranteModel> listar() {

		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

//	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", name = "projecao", paramType = "query", dataType="java.lang.String")
//	})
//	//@JsonView(RestauranteView.Resumo.class)
//	@GetMapping(params = "projecao=resumo")
//	public List<RestauranteModel> listarResumido() {
//
//		return listar();
//	}

	@ApiOperation(value = "Lista restaurantes", hidden = true)
	//@JsonView(RestauranteView.ApenasNome.class)
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
		return restauranteApenasNomeModelAssembler
				.toCollectionModel(restauranteRepository.findAll());
	}


//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//		if("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//
//
//		return restaurantesWrapper;
//	}



	/**Esse codigo comenmtado é para quando quiser fazer projeção de rescursos, o exemplo não comentado é quando quer fazer dinamico*/

//	@GetMapping
//	public List<RestauranteModel> listar() {
//
//		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//	}
//
//	@JsonView(RestauranteView.Resumo.class)
//	@GetMapping(params = "projecao=resumo")
//	public List<RestauranteModel> listarResumido() {
//
//		return listar();
//	}
//

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {

		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		return restauranteModelAssembler.toModel(restaurante);
	}

	@PostMapping
	public RestauranteModel adicionar(@Valid @RequestBody RestauranteInput restauranteInput) {

		try {
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(
					restauranteInputDisassembler.toDomainObject(restauranteInput))
			);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
								 @Valid @RequestBody RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

		restauranteInputDisassembler.copyToDomainObject(restauranteInput,restauranteAtual);

		try {
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

//	@PatchMapping("{restauranteId}")
//	public RestauranteModel atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
//										HttpServletRequest request) {
//
//		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
//
//		merge(campos, restauranteAtual, request);
//		validate(restauranteAtual, "restaurante");
//
//		return atualizar(restauranteId, restauranteAtual);
//	}

	private void validate(Restaurante restaurante, String objectName) {

		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);

		validator.validate(restaurante, bindingResult);

		if(bindingResult.hasErrors()) {
			throw new ValidationException(String.valueOf(bindingResult));
		}
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

			Restaurante restauranteOrigem  = objectMapper.convertValue(camposOrigem, Restaurante.class);

			camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {

				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	@GetMapping("/busca-nome-frete")
	public List<Restaurante> restaurantePorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		
		
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@GetMapping("/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		
		return restauranteRepository.buscarPrimeiro();
	}

	@Override
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fechar(restauranteId);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {

		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e.getCause());
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {

		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e.getCause());
		}
	}

}
