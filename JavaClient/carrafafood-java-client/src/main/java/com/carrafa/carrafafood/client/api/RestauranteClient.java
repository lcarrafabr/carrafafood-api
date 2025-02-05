package com.carrafa.carrafafood.client.api;

import com.carrafa.carrafafood.client.model.RestauranteResumoModel;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class RestauranteClient {

    private static final String RESOURCE_PATH = "/restaurantes";

    private String url;

    private RestTemplate restTemplate;

    public RestauranteClient(RestTemplate restTemplate, String s) {
        this.restTemplate = restTemplate;
        this.url = s;
    }

    public List<RestauranteResumoModel> listar() {

        URI resourceUri = URI.create(url.concat(RESOURCE_PATH));

        RestauranteResumoModel[] restaurantes = restTemplate.getForObject(resourceUri, RestauranteResumoModel[].class);

        return Arrays.asList(restaurantes);
    }
}
