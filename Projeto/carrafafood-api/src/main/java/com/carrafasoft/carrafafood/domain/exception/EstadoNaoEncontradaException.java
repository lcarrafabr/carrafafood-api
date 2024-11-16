package com.carrafasoft.carrafafood.domain.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public static final String NAO_EXISTE_CADASTRO_COM_ID = "Não existe cadastro de estado com o código %d";

		public EstadoNaoEncontradaException(String mensagem) {

		super(mensagem);
	}

	public EstadoNaoEncontradaException(Long estadoId) {

			this(String.format(NAO_EXISTE_CADASTRO_COM_ID, estadoId));
	}
}
