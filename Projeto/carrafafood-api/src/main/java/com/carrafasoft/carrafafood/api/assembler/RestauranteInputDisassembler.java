package com.carrafasoft.carrafafood.api.assembler;

import com.carrafasoft.carrafafood.api.model.input.RestauranteInput;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {

        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {

        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteInput, restaurante);
    }
}