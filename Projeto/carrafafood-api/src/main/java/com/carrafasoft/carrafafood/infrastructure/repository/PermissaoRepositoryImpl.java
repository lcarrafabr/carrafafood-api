package com.carrafasoft.carrafafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.carrafasoft.carrafafood.domain.model.Permissao;
import com.carrafasoft.carrafafood.domain.repository.PermissaoRepository;

public class PermissaoRepositoryImpl implements PermissaoRepository{
	
	@PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<Permissao> listar() {
        return manager.createQuery("from Permissao", Permissao.class)
                .getResultList();
    }
    
    @Override
    public Permissao buscar(Long id) {
        return manager.find(Permissao.class, id);
    }
    
    @Transactional
    @Override
    public Permissao salvar(Permissao permissao) {
        return manager.merge(permissao);
    }
    
    @Transactional
    @Override
    public void remover(Permissao permissao) {
        permissao = buscar(permissao.getId());
        manager.remove(permissao);
    }

}