package br.com.senior.techicaltest.vendas.pedido.listeners;

import br.com.senior.techicaltest.vendas.pedido.StatusPedidoEnum;
import br.com.senior.techicaltest.vendas.pedido.event.PedidoPreSaveEvent;
import br.com.senior.techicaltest.vendas.venda.Venda;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class AplicarTotalizadoresPreSaveListener {

    @EventListener
    public void setValorTotal(PedidoPreSaveEvent event) {
        var pedido = event.getPedido();
        var valorTotalPedido = pedido.getVendaSet()
                .stream().map(Venda::getValorTotalVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var valorTotalDesconto = event.getPedido().getVendaSet()
                .stream()
                .filter(venda -> Objects.nonNull(venda.getValorDesconto()))
                .map(Venda::getValorDesconto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotalPedido(valorTotalPedido.subtract(valorTotalDesconto));
    }

    @EventListener
    public void setStatusDefault(PedidoPreSaveEvent event) {
        var pedido = event.getPedido();
        pedido.setStatusPedidoEnum(StatusPedidoEnum.ABERTO);
    }
}
