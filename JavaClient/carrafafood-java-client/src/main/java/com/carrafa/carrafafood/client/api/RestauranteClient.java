package com.carrafa.carrafafood.client.api;

import com.carrafa.carrafafood.client.model.RestauranteModel;
import com.carrafa.carrafafood.client.model.RestauranteResumoModel;
import com.carrafa.carrafafood.client.model.input.RestauranteInput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class RestauranteClient {

    private static final String RESOURCE_PATH = "/restaurantes";


    private RestTemplate restTemplate;
    private String url;

    public List<RestauranteResumoModel> listar() {

        try {
            URI resourceUri = URI.create(url.concat(RESOURCE_PATH));

            RestauranteResumoModel[] restaurantes = restTemplate.getForObject(resourceUri, RestauranteResumoModel[].class);

            return Arrays.asList(restaurantes);
        } catch (RestClientResponseException e) {

            throw new ClientApiException(e.getMessage(), e);
        }
    }

    public RestauranteModel adicionar(RestauranteInput restaurante) {
        var resourceUri = URI.create(url + RESOURCE_PATH);

        try {
            return restTemplate
                    .postForObject(resourceUri, restaurante, RestauranteModel.class);
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
}
