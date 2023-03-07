package com.carrafasoft.carrafafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.carrafasoft.carrafafood.CarrafafoodApiApplication;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(CarrafafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> cozinhas = cadastroCozinha.listar();
		
		Cozinha cozinha01 = new Cozinha();
		cozinha01.setNome("Japonesa");
		
		Cozinha cozinha02 = new Cozinha();
		cozinha02.setNome("Italiana");
		
		cozinha01 = cadastroCozinha.salvar(cozinha01);
		cozinha02 = cadastroCozinha.salvar(cozinha02);
		
		System.out.printf("%d - %s\n", cozinha01.getId(), cozinha01.getNome());
		System.out.printf("%d - %s\n", cozinha02.getId(), cozinha02.getNome());
	}

}
