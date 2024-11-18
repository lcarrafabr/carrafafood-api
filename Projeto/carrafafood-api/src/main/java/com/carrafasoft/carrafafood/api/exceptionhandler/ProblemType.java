package com.carrafasoft.carrafafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NA_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encntrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");

    private String title;
    private String uri;

    ProblemType(String path, String title) {

        this.uri = "https://carrafafod.com.br" + path;
        this.title = title;
    }
}
