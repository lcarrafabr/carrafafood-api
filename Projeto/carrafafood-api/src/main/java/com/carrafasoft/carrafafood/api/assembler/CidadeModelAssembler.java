package com.carrafasoft.carrafafood.api.assembler;

import com.carrafasoft.carrafafood.api.AlgaLinks;
import com.carrafasoft.carrafafood.api.controller.CidadeController;
import com.carrafasoft.carrafafood.api.controller.EstadoController;
import com.carrafasoft.carrafafood.api.model.dto.CidadeModel;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }


    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(algaLinks.linkToCidades("cidades"));

        cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));

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
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToCidades());
    }
}
