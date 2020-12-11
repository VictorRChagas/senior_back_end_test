package br.com.senior.techicaltest.vendas.pedido.event;

import br.com.senior.techicaltest.vendas.pedido.model.Pedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class PedidoPreSaveEvent extends ApplicationEvent {

    @Getter
    private final Pedido pedido;

    public PedidoPreSaveEvent(Object source, Pedido pedido) {
        super(source);
        this.pedido = pedido;
    }
}
