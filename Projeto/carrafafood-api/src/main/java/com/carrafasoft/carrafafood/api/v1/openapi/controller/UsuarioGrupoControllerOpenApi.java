package com.carrafasoft.carrafafood.api.v1.openapi.controller;

import com.carrafasoft.carrafafood.api.exceptionhandler.Problem;
import com.carrafasoft.carrafafood.api.v1.model.dto.GrupoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<GrupoModel> listar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    @ApiOperation("Desassociação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,

            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId);

    @ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,

            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId);
}
