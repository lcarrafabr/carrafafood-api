package com.carrafasoft.carrafafood.domain.repository;

import java.util.List;

import com.carrafasoft.carrafafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> listar();
    Permissao buscar(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);

}
