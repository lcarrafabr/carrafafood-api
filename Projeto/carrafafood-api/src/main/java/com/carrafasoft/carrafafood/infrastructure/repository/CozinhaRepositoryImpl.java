package com.carrafasoft.carrafafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.repository.CozinhaRepository;

@Component //O ideal é usar @Reporitory. Mas será abordado em uma aula a frente
public class CozinhaRepositoryImpl implements CozinhaRepository{

	
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> listar() {
		
		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
		
		return query.getResultList();		
	}
	
	@Override
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		
		return manager.merge(cozinha);
	}
	
	@Override
	public Cozinha buscar(Long id) {
		
		
		return manager.find(Cozinha.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Cozinha cozinha) {
		
		cozinha = buscar(cozinha.getId());
		manager.remove(cozinha);	
	}


}
