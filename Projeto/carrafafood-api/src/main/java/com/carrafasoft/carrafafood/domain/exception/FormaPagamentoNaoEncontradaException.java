package com.carrafasoft.carrafafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    private static final String  NAO_EXISTE_CADASTRO_FORMA_PAGAMENETO = "Não existe um cadastro de forma de pagamento com código %d";

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format(NAO_EXISTE_CADASTRO_FORMA_PAGAMENETO, formaPagamentoId));
    }

}
