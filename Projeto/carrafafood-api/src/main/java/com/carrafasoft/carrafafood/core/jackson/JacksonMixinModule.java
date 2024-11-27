package com.carrafasoft.carrafafood.core.jackson;

import com.carrafasoft.carrafafood.domain.model.Cidade;
import com.carrafasoft.carrafafood.domain.model.Cozinha;
import com.carrafasoft.carrafafood.domain.model.Restaurante;
import com.carrafasoft.carrafafood.domain.model.mixin.CidadeMixin;
import com.carrafasoft.carrafafood.domain.model.mixin.CozinhaMixin;
import com.carrafasoft.carrafafood.domain.model.mixin.RestautanteMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {

        setMixInAnnotation(Restaurante.class, RestautanteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
