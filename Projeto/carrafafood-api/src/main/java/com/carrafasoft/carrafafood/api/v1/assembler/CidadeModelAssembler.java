package com.carrafasoft.carrafafood.api.v1.assembler;

import com.carrafasoft.carrafafood.api.v1.AlgaLinks;
import com.carrafasoft.carrafafood.api.v1.controller.CidadeController;
import com.carrafasoft.carrafafood.api.v1.model.dto.CidadeModel;
import com.carrafasoft.carrafafood.core.security.AlgaSecurity;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }


    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        if (algaSecurity.podeConsultarCidades()) {
            cidadeModel.add(algaLinks.linkToCidades("cidades"));
        }

        if (algaSecurity.podeConsultarEstados()) {
            cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
        }

        return cidadeModel;
    }

//    @Override
//    public CidadeModel toModel(Cidade cidade) {
//        CidadeModel cidadeModel = modelMapper.map(cidade, CidadeModel.class);
//
//        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
//                .buscar(cidadeModel.getId())).withSelfRel();//01
//
//        Link linkCidade = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
//                .listar()).withRel("cidades");//02
//
//        Link linkEstado = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
//                .buscar(cidadeModel.getEstado().getId())).withSelfRel();//03
//
//
//        cidadeModel.add(link);//01
//        cidadeModel.add(linkCidade);//02
//        cidadeModel.getEstado().add(linkEstado);//03
//
//        return cidadeModel;
//    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarCidades()) {
            collectionModel.add(algaLinks.linkToCidades());
        }

        return collectionModel;
    }
}
