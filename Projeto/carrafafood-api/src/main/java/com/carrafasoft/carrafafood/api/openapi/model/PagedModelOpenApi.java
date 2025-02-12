package com.carrafasoft.carrafafood.api.openapi.model;

import com.carrafasoft.carrafafood.api.model.dto.CozinhaModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(example = "0", value = "quantidade de registros por pagina.")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total de páginas")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Número da pagina começa em (0) zero")
    private Long number;
}
