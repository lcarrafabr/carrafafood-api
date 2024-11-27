package com.carrafasoft.carrafafood.domain.model.mixin;

import com.carrafasoft.carrafafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
}
