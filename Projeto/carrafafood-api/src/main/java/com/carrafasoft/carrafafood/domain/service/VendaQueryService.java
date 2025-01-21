package com.carrafasoft.carrafafood.domain.service;

import com.carrafasoft.carrafafood.domain.filter.VendaDiariaFilter;
import com.carrafasoft.carrafafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
}
