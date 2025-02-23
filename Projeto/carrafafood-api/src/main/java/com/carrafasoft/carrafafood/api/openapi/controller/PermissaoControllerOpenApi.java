package com.carrafasoft.carrafafood.api.openapi.controller;

import com.carrafasoft.carrafafood.domain.model.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

    @Api(tags = "Permissões")
    public interface PermissaoControllerOpenApi {

        @ApiOperation("Lista as permissões")
        CollectionModel<PermissaoModel> listar();

    }

