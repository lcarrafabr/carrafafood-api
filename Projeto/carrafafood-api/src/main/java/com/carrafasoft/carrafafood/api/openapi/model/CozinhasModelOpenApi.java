package com.carrafasoft.carrafafood.api.openapi.model;

import com.carrafasoft.carrafafood.api.model.dto.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
//public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaModel>{// alterado na aula 19.41 após incluir hateoas

@Getter
@Setter
public class CozinhasModelOpenApi {

    private CozinhasModelOpenApi _embedded;
    private Links _links;
    private PagedModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {

        List<CozinhaModel> cozinhas;

    }
}
