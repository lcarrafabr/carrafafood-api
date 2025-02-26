package com.carrafasoft.carrafafood.api.v1.controller;

import com.carrafasoft.carrafafood.api.v1.AlgaLinks;
import com.carrafasoft.carrafafood.api.v1.assembler.PermissaoModelAssembler;
import com.carrafasoft.carrafafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.carrafasoft.carrafafood.domain.model.Grupo;
import com.carrafasoft.carrafafood.domain.model.PermissaoModel;
import com.carrafasoft.carrafafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

}
