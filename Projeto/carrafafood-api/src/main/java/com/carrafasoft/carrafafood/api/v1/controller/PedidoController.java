package com.carrafasoft.carrafafood.api.v1.controller;

import com.carrafasoft.carrafafood.api.v1.assembler.PedidoInputDisassembler;
import com.carrafasoft.carrafafood.api.v1.assembler.PedidoModelAssembler;
import com.carrafasoft.carrafafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.carrafasoft.carrafafood.api.v1.model.dto.PedidoModel;
import com.carrafasoft.carrafafood.api.v1.model.dto.PedidoResumoModel;
import com.carrafasoft.carrafafood.api.v1.model.input.PedidoInput;
import com.carrafasoft.carrafafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.carrafasoft.carrafafood.core.data.PageWrapper;
import com.carrafasoft.carrafafood.core.data.PageableTranslator;
import com.carrafasoft.carrafafood.core.security.AlgaSecurity;
import com.carrafasoft.carrafafood.core.security.CheckSecurity;
import com.carrafasoft.carrafafood.domain.exception.EntidadeNaoEncontradaException;
import com.carrafasoft.carrafafood.domain.exception.NegocioException;
import com.carrafasoft.carrafafood.domain.model.Pedido;
import com.carrafasoft.carrafafood.domain.model.Usuario;
import com.carrafasoft.carrafafood.domain.repository.PedidoRepository;
import com.carrafasoft.carrafafood.domain.filter.PedidoFilter;
import com.carrafasoft.carrafafood.domain.service.EmissaoPedidoService;
import com.carrafasoft.carrafafood.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/pedidos")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Autowired
    private AlgaSecurity algaSecurity;

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false, name = "campos") String campos) {
//        List<Pedido> todosPedidos = pedidoRepository.findAll();
//        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNoneBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string")
    })
    @CheckSecurity.Pedidos.PodePesquisar
    @Override
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                                   @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageble(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(
                PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(algaSecurity.getUsuarioId());

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


    private Pageable traduzirPageble(Pageable apiPageable) {

        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.tranlate(apiPageable, mapeamento);
    }
}
