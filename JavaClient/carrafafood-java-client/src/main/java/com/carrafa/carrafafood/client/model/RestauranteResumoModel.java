package com.carrafa.carrafafood.client.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteResumoModel {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModel cozinhaModel;
}
