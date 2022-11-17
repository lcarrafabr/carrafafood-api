package com.carrafasoft.carrafafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.carrafasoft.carrafafood.CarrafafoodApiApplication;
import com.carrafasoft.carrafafood.domain.model.Cozinha;

public class BuscaCozinhaMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(CarrafafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		Cozinha cozinhas = cadastroCozinha.buscar(1L);
		
		System.out.println(cozinhas.getNome());
	}

}
