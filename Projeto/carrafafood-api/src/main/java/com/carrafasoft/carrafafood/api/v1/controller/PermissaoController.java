package com.carrafasoft.carrafafood.api.v1.controller;

import com.carrafasoft.carrafafood.api.v1.assembler.PermissaoModelAssembler;
import com.carrafasoft.carrafafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.carrafasoft.carrafafood.domain.model.Permissao;
import com.carrafasoft.carrafafood.domain.model.PermissaoModel;
import com.carrafasoft.carrafafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();

        return permissaoModelAssembler.toCollectionModel(todasPermissoes);
    }
}
