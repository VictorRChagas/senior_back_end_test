package br.com.senior.techicaltest.vendas.pedido.listeners;

import br.com.senior.techicaltest.vendas.pedido.event.PedidoPreSaveEvent;
import br.com.senior.techicaltest.vendas.venda.Venda;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AplicarTotalizadoresPreSaveListener {

    @EventListener
    public void setValorTotal(PedidoPreSaveEvent event) {
        var pedido = event.getPedido();
        var valorTotalPedido = pedido.getVendaSet()
                .stream().map(Venda::getValorTotalVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotalPedido(valorTotalPedido);
    }
}
