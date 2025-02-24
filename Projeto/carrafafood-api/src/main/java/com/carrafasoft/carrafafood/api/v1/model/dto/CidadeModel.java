package com.carrafasoft.carrafafood.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @ApiModelProperty(value = "ID da cidade", example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "São Paulo", required = true)
    private String nome;
    private EstadoModel estado;

}
