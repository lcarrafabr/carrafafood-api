package com.carrafasoft.carrafafood.api.v1.openapi.controller;

import com.carrafasoft.carrafafood.api.exceptionhandler.Problem;
import com.carrafasoft.carrafafood.api.v1.model.dto.FormaPagamentoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<FormaPagamentoModel> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado",
                    content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado",
                    content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,

            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);
}