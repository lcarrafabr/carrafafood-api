package com.carrafasoft.carrafafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.carrafasoft.carrafafood.api.assembler.RestauranteInputDisassembler;
import com.carrafasoft.carrafafood.api.assembler.RestauranteModelAssembler;
import com.carrafasoft.carrafafood.api.model.dto.CozinhaModel;
import com.carrafasoft.carrafafood.api.model.dto.RestauranteModel;
import com.carrafasoft.carrafafood.api.model.input.RestauranteInput;
import com.carrafasoft.carrafafood.domain.exception.NegocioException;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.model.Restaurante;
import com.carrafasoft.carrafafood.domain.repository.RestauranteRepository;
import com.carrafasoft.carrafafood.domain.service.CadastroRestauranteService;
import com.carrafasoft.carrafafood.infrastructure.repository.spec.RestauranteSpecs;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private SmartValidator validator;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@GetMapping
	public List<RestauranteModel> listar() {
		
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {

		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

		return restauranteModelAssembler.toModel(restaurante);
	}

	@PostMapping
	public RestauranteModel adicionar(@Valid @RequestBody RestauranteInput restauranteInput) {

		try {
			return restauranteModelAssembler.toModel(restauranteService.salvar(
					restauranteInputDisassembler.toDomainObject(restauranteInput))
			);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
								 @Valid @RequestBody RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);

		restauranteInputDisassembler.copyToDomainObject(restauranteInput,restauranteAtual);

		try {
			return restauranteModelAssembler.toModel(restauranteService.salvar(restauranteAtual));
		} catch (EntidadeNaoEncontradaException e) {
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

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {

		restauranteService.ativar(restauranteId);
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {

		restauranteService.inativar(restauranteId);
	}

}
