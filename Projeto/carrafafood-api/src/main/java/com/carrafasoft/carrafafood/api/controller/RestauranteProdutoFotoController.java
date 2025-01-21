package com.carrafasoft.carrafafood.api.controller;

import com.carrafasoft.carrafafood.api.model.input.FotoProdutoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                              @Valid FotoProdutoInput fotoProdutoInput) {

        try {
            var nomeArquivo = UUID.randomUUID().toString()
                    + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

            var arquivoFoto = Path.of("E:/PROJETOS/Algaworks/Especialista_Spring_Rest/catalogo", nomeArquivo);

            System.out.println(fotoProdutoInput.getDescricao());
            System.out.println(arquivoFoto);
            System.out.println(fotoProdutoInput.getArquivo().getContentType());

            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
