package com.carrafasoft.carrafafood.domain.repository;

import com.carrafasoft.carrafafood.domain.model.FotoProduto;
import com.carrafasoft.carrafafood.domain.model.Produto;
import com.carrafasoft.carrafafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoReprositoryQueries {

    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(@Param("restaurante") Long restauranteId,
                               @Param("produto") Long produtoId);

    List<Produto> findTodosByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(Restaurante restaurante);

    @Query("select f from FotoProduto f join f.produto p " +
            "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId ")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
}