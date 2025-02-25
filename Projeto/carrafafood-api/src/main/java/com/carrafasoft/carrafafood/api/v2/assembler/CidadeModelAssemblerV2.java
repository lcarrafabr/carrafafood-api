package com.carrafasoft.carrafafood.api.v2.assembler;

import com.carrafasoft.carrafafood.api.v1.AlgaLinks;
import com.carrafasoft.carrafafood.api.v1.controller.CidadeController;
import com.carrafasoft.carrafafood.api.v1.model.dto.CidadeModel;
import com.carrafasoft.carrafafood.api.v2.AlgaLinksV2;
import com.carrafasoft.carrafafood.api.v2.controller.CidadeControllerV2;
import com.carrafasoft.carrafafood.api.v2.model.CidadeModelV2;
import com.carrafasoft.carrafafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CidadeModelAssemblerV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }


    @Override
    public CidadeModelV2 toModel(Cidade cidade) {
        CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(algaLinks.linkToCidades("cidades"));

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
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToCidades());
    }
}
