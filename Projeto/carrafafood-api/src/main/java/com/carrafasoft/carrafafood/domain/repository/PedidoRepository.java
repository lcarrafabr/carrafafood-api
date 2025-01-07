package com.carrafasoft.carrafafood.domain.repository;

import com.carrafasoft.carrafafood.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
