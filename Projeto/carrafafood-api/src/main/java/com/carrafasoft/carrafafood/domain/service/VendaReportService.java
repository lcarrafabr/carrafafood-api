package com.carrafasoft.carrafafood.domain.service;

import com.carrafasoft.carrafafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);

    byte[] emitirVendasDiariasToExcel(VendaDiariaFilter filtro, String timeOffSet);
}
