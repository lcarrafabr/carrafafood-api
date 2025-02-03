package com.carrafasoft.carrafafood.domain.event;

import com.carrafasoft.carrafafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

    private Pedido pedido;
}
