package com.carrafa.carrafafood.client;

import com.carrafa.carrafafood.client.api.ClientApiException;
import com.carrafa.carrafafood.client.api.RestauranteClient;
import org.springframework.web.client.RestTemplate;

public class ListagemRestaurantesMain {

    public static void main(String[] args) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            RestauranteClient restauranteClient = new RestauranteClient(restTemplate, "http://localhost:8080");

            restauranteClient.listar().stream()
                    .forEach(restaurante -> System.out.println(restaurante));
        } catch (ClientApiException e) {
            if(e.getProblem() != null) {
                System.out.println("status: " + e.getProblem().getStatus());
                System.out.println("timestamp: " + e.getProblem().getTimestamp());
                System.out.println("message: " + e.getProblem().getUserMessage());
            } else {
                System.out.println("Erro desconhecido.");
                e.printStackTrace();
            }
        }
    }
}
