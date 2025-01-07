package com.carrafasoft.carrafafood.domain.service;

import com.carrafasoft.carrafafood.domain.exception.NegocioException;
import com.carrafasoft.carrafafood.domain.model.Pedido;
import com.carrafasoft.carrafafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    private static String STATUS_IVALIDO = "O Status do pedido %d não pode ser alterado para de %s para %s";

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long perdidoId) {

        Pedido pedido = emissaoPedido.buscarOuFalhar(perdidoId);

        if(!pedido.getStatus().equals(StatusPedido.CRIADO)){
            throw new NegocioException(String.format(STATUS_IVALIDO,
                    pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(), pedido.getStatus().getDescricao(),
                            StatusPedido.CANCELADO.getDescricao()));
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataCancelamento(OffsetDateTime.now());
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(), pedido.getStatus().getDescricao(),
                            StatusPedido.ENTREGUE.getDescricao()));
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataEntrega(OffsetDateTime.now());
    }
}
