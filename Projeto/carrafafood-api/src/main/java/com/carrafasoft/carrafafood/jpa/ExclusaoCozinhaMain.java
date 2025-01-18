package com.carrafasoft.carrafafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.carrafasoft.carrafafood.CarrafafoodApiApplication;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.model.mixin.repository.CozinhaRepository;

public class ExclusaoCozinhaMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(CarrafafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha01 = new Cozinha();
		cozinha01.setId(3L);
		cozinha01.setNome("Marroquina");
		
		cozinha01 = cadastroCozinha.save(cozinha01);
		
		System.out.printf("%d - %s\n", cozinha01.getId(), cozinha01.getNome());
	}

}
