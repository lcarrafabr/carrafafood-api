package com.carrafasoft.carrafafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe cadastro de cozinha com o código %d";

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {

        this(String.format(NAO_EXISTE_CADASTRO_COM_ID, cozinhaId));
    }
}
