package com.carrafasoft.carrafafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoModel {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "São Caetano do Sul", required = true)
    private String nome;

}
