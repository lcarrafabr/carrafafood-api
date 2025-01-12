package com.carrafasoft.carrafafood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectmapper) {

        Squiggly.init(objectmapper, new RequestSquigglyContextProvider());

        //Se colocar essa linha so funciona nas URLs informadas, se tirar funciona no projeto inteiro
        var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

        var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();

        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);
        filterRegistration.setUrlPatterns(urlPatterns); // Se ativar o urlPatter tem que coocar essa linha

        return filterRegistration;
    }
}
