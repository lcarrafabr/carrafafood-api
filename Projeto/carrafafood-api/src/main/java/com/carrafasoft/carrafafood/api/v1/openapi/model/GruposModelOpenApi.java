package com.carrafasoft.carrafafood.api.v1.openapi.model;

import com.carrafasoft.carrafafood.api.v1.model.dto.GrupoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

    private GruposEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GruposEmbeddedModel")
    @Data
    public class GruposEmbeddedModelOpenApi {

        private List<GrupoModel> grupos;

    }
}
