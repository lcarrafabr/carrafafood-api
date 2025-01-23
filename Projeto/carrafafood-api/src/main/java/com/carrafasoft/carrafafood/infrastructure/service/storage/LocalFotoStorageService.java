package com.carrafasoft.carrafafood.infrastructure.service.storage;

import com.carrafasoft.carrafafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${carrafafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

    @Override
    public void armazenar(NovaFoto novaFoto) {



            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

        try {
            FileCopyUtils.copy(novaFoto.getInputStream(),
                    Files.newOutputStream(arquivoPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}
