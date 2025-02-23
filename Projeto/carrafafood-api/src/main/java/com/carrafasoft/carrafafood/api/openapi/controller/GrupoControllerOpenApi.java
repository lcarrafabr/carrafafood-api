package com.carrafasoft.carrafafood.api.openapi.controller;

import com.carrafasoft.carrafafood.api.exceptionhandler.Problem;
import com.carrafasoft.carrafafood.api.model.dto.GrupoModel;
import com.carrafasoft.carrafafood.api.model.input.GrupoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

    @Api(tags = "Grupos")
    public interface GrupoControllerOpenApi {

        @ApiOperation("Lista os grupos")
        CollectionModel<GrupoModel> listar();

        @ApiOperation("Busca um grupo por ID")
        @ApiResponses({
                @ApiResponse(responseCode = "400", description = "ID da grupo inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
                @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
        })
        GrupoModel buscar(
                @ApiParam(value = "ID de um grupo", example = "1")
                Long grupoId);

        @ApiOperation("Cadastra um grupo")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Grupo cadastrado")
        })
        GrupoModel adicionar(
                @ApiParam(name = "corpo", value = "Representação de um novo grupo")
                GrupoInput grupoInput);

        @ApiOperation("Atualiza um grupo por ID")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Grupo atualizadoo"),
                @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
        })
        GrupoModel atualizar(
                @ApiParam(value = "ID de um grupo", example = "1")
                Long grupoId,

                @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
                GrupoInput grupoInput);

        @ApiOperation("Exclui um grupo por ID")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Grupo excluído"),
                @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
        })
        void remover(
                @ApiParam(value = "ID de um grupo", example = "1")
                Long grupoId);
    }

