package com.carrafasoft.carrafafood.domain.repository;

import com.carrafasoft.carrafafood.domain.model.FotoProduto;

public interface ProdutoReprositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);
}
