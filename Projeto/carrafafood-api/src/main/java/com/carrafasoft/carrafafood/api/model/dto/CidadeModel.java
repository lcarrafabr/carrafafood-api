package com.carrafasoft.carrafafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeModel {

    @ApiModelProperty(value = "ID da cidade", example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "São Paulo", required = true)
    private String nome;
    private EstadoModel estado;

}
