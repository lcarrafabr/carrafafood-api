package com.carrafasoft.carrafafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.carrafasoft.carrafafood.CarrafafoodApiApplication;
import com.carrafasoft.carrafafood.domain.model.Cozinha;

public class AlteracaoCozinhaMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(CarrafafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		Cozinha cozinha01 = new Cozinha();
		cozinha01.setId(3L);
		cozinha01.setNome("Marroquina");
		
		cozinha01 = cadastroCozinha.salvar(cozinha01);
		
		System.out.printf("%d - %s\n", cozinha01.getId(), cozinha01.getNome());
	}

}
