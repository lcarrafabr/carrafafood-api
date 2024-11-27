package com.carrafasoft.carrafafood;

import com.carrafasoft.carrafafood.domain.exception.EntidadeEmUsoException;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCozinhaIntegrationTest {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void testarCadastroCozinhaComSucesso() {

		//Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("chinesa");

		//Ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		//Validação
		Assertions.assertThat(novaCozinha).isNotNull();
		Assertions.assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		try {
			cadastroCozinha.salvar(novaCozinha);
			Assertions.fail("Deveria dar erro");
		} catch (Exception e) {
			Assertions.assertThat(e.getClass()).isEqualTo(ConstraintViolationException.class);
		}
	}

	@Test()
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		cadastroCozinha.excluir(1L);
	}

}
