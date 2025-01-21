package com.carrafasoft.carrafafood.infrastructure.service.report;

import com.carrafasoft.carrafafood.domain.filter.VendaDiariaFilter;
import com.carrafasoft.carrafafood.domain.service.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class PdfVendaReportService implements VendaReportService {


    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet) {
        return null;
    }
}
