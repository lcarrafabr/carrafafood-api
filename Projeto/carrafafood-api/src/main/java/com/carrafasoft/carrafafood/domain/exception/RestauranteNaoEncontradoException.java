package com.carrafasoft.carrafafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe cadastro de restaurante com o código %d";

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {

        this(String.format(NAO_EXISTE_CADASTRO_COM_ID, restauranteId));
    }
}
