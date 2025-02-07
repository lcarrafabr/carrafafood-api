package com.carrafasoft.carrafafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "http://carrafafood.com.br/dados-invalidos")
    private String type;

    @ApiModelProperty(example = "Dados inválidos")
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos")
    private String detail;

    @ApiModelProperty(example = "2025-02-07T16:30:00.70844Z")
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos")
    private String userMessage;

    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {

        private String name;
        private String userMessage;
    }
}
