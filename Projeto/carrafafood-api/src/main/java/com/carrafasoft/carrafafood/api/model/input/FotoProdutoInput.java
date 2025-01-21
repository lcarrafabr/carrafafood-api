package com.carrafasoft.carrafafood.api.model.input;

import com.carrafasoft.carrafafood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "1MB")
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
