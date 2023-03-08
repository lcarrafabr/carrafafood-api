package com.carrafasoft.carrafafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

@RestController
//@RequestMapping(value = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@GetMapping()
	public List<Cozinha> listar() {

		return cozinhaRepository.listar();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {

		return new CozinhasXmlWrapper(cozinhaRepository.listar());
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {

		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		if (cozinha == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {

		return cozinhaService.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {

		Cozinha cozinhaSalva = cozinhaRepository.buscar(cozinhaId);

		if (cozinhaSalva != null) {
			// cozinhaSalva.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaSalva, "id");
			cozinhaRepository.salvar(cozinhaSalva);
			return ResponseEntity.ok(cozinhaSalva);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {

		try {
			cozinhaService.excluir(cozinhaId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} 

	}

}
