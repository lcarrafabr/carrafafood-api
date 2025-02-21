package com.carrafasoft.carrafafood.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoModel {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Gerente")
    private String nome;

}
