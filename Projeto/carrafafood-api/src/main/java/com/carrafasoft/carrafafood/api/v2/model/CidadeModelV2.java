package com.carrafasoft.carrafafood.api.v2.model;

import com.carrafasoft.carrafafood.api.v1.model.dto.EstadoModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@ApiModel("CidadeModel")
@Setter
@Getter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

    @ApiModelProperty(value = "ID da cidade", example = "1", required = true)
    private Long idCidade;

    @ApiModelProperty(example = "São Paulo", required = true)
    private String nomeCidade;

    private Long idEstado;

    private String nomeEstado;
}
