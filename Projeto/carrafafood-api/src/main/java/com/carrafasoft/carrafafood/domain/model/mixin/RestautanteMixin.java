package com.carrafasoft.carrafafood.domain.model.mixin;

import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.model.Endereco;
import com.carrafasoft.carrafafood.domain.model.FormaPagamento;
import com.carrafasoft.carrafafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestautanteMixin {

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();

    @JsonIgnore
    private Endereco endereco;
}
