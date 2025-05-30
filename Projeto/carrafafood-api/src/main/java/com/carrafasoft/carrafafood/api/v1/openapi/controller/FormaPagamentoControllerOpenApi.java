package com.carrafasoft.carrafafood.api.v1.openapi.controller;

import com.carrafasoft.carrafafood.api.exceptionhandler.Problem;
import com.carrafasoft.carrafafood.api.v1.model.dto.FormaPagamentoModel;
import com.carrafasoft.carrafafood.api.v1.model.input.FormaPagamentoInput;
import com.carrafasoft.carrafafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de pagamento")
    public interface FormaPagamentoControllerOpenApi {

        @ApiOperation(value = "Lista as formas de pagamento")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = FormasPagamentoModelOpenApi.class)))
        })
        ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

        @ApiOperation("Busca uma forma de pagamento por ID")
        @ApiResponses({
                @ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
                @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Problem.class)))
        })
        ResponseEntity<FormaPagamentoModel> buscar(
                @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
                Long formaPagamentoId,

                ServletWebRequest request);

        @ApiOperation("Cadastra uma forma de pagamento")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastradao")
        })
        FormaPagamentoModel adicionar(
                @ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true)
                FormaPagamentoInput formaPagamentoInput);

        @ApiOperation("Atualiza uma cidade por ID")
        @ApiResponses({
                @ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
                @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Problem.class)))
        })
        FormaPagamentoModel atualizar(
                @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
                Long formaPagamentoId,

                @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados")
                FormaPagamentoInput formaPagamentoInput);

        @ApiOperation("Exclui uma forma de pagamento por ID")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
                @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontradaa", content = @Content(schema = @Schema(implementation = Problem.class)))
        })
        void remover(Long formaPagamentoId);
    }

