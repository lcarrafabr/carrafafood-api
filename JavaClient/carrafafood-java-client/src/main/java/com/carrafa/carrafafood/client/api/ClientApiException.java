package com.carrafa.carrafafood.client.api;

import com.carrafa.carrafafood.client.model.Problem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
public class ClientApiException extends RuntimeException{

    private static final String NAO_FOI_POSSIVEL_DESSERIALIZAR_RESPOSTA = "Não foi possível desserializar a resposta em um problema.";

    @Getter
    private Problem problem;
    public ClientApiException(String message, RestClientResponseException cause) {
        super(message, cause);
        deserializeProblem(cause);
    }

    private void deserializeProblem(RestClientResponseException cause) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new JavaTimeModule());
            mapper.findAndRegisterModules();

            this.problem = mapper.readValue(cause.getResponseBodyAsString(), Problem.class);

        } catch (JsonProcessingException e) {
            log.warn(NAO_FOI_POSSIVEL_DESSERIALIZAR_RESPOSTA);
        }
    }
}
