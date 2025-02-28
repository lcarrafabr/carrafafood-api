package com.carrafasoft.carrafafood.domain.listener;

import com.carrafasoft.carrafafood.domain.event.PedidoConfirmadoEvent;
import com.carrafasoft.carrafafood.domain.model.Pedido;
import com.carrafasoft.carrafafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    //@EventListener // Com essa anotação, o email será enviado antes de salvar a confirmação. Se der rollback o email ja foi enviado
    @TransactionalEventListener // COm essa anotação o email só é enviado após a trasnsação, mas se der erro no email, ja foi comitado a transação
    public void aoCOnfirmarPedido(PedidoConfirmadoEvent event) {

        Pedido pedido = event.getPedido();

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado.")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);

    }
}
