package com.carrafasoft.carrafafood.api.controller;

import com.carrafasoft.carrafafood.domain.filter.VendaDiariaFilter;
import com.carrafasoft.carrafafood.domain.model.dto.VendaDiaria;
import com.carrafasoft.carrafafood.domain.service.VendaQueryService;
import com.carrafasoft.carrafafood.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {

        return vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffSet);

        var headers = new HttpHeaders();

        //attachment é para download e não embutido no navegador
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.xlsx");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }


    @GetMapping(path = "/vendas-diarias", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> consultarVendasDiariasExcel(
            VendaDiariaFilter filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {

        // Gerar relatório em Excel
        byte[] relatorio = vendaReportService.emitirVendasDiariasToExcel(filtro, timeOffSet);

        // Configuração do cabeçalho
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.xlsx");

        // Retornar o relatório em Excel
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .headers(headers)
                .body(relatorio);
    }

}
