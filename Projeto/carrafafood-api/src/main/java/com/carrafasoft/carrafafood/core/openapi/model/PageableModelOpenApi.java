package com.carrafasoft.carrafafood.core.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Getter
@Setter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Número da pagina (começa em '0' zero)")
    private int page;

    @ApiModelProperty(example = "10", value = "Quantidade de elementos por pagina")
    private int size;

    @ApiModelProperty(example = "asc", value = "nome da propriedadee para ordenação")
    private List<String> sort;
}
