package com.carrafasoft.carrafafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe cadastro de cidade com o código %d";

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long cidadeId) {

        this(String.format(NAO_EXISTE_CADASTRO_COM_ID, cidadeId));
    }
}
