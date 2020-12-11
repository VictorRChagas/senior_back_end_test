package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.pedido.event.PedidoPreSaveEvent;
import br.com.senior.techicaltest.vendas.pedido.listeners.AplicarTotalizadoresPreSaveListener;
import br.com.senior.techicaltest.vendas.venda.TipoVendaEnum;
import br.com.senior.techicaltest.vendas.venda.Venda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

public class AplicarTotalizadoresListenerTest {

    AplicarTotalizadoresPreSaveListener aplicarTotalizadores = new AplicarTotalizadoresPreSaveListener();

    @Test
    public void checkIfCorrectValueIsSet() {
        var pedidoDefault = getPedidoDefault();
        var pedidoPreSaveEvent = new PedidoPreSaveEvent(this, pedidoDefault);
        aplicarTotalizadores.setValorTotal(pedidoPreSaveEvent);
        Assertions.assertEquals(BigDecimal.valueOf(20), pedidoDefault.getValorTotalPedido());
    }

    @Test
    public void checkIfCorrectValueIsCorrectlyAppliedWithDiscounts() {
        var pedidoDefault = getPedidoDefault();
        pedidoDefault.getVendaSet().stream()
                .findFirst()
                .ifPresent(venda -> venda.setValorDesconto(BigDecimal.valueOf(5)));

        var pedidoPreSaveEvent = new PedidoPreSaveEvent(this, pedidoDefault);
        aplicarTotalizadores.setValorTotal(pedidoPreSaveEvent);

        Assertions.assertEquals(BigDecimal.valueOf(15), pedidoDefault.getValorTotalPedido());
    }

    @Test
    public void checkIfDefaultStatusIsSet() {
        var pedidoDefault = getPedidoDefault();
        var pedidoPreSaveEvent = new PedidoPreSaveEvent(this, pedidoDefault);
        aplicarTotalizadores.setStatusDefault(pedidoPreSaveEvent);

        Assertions.assertEquals(StatusPedidoEnum.ABERTO, pedidoDefault.getStatusPedidoEnum());
    }


    public Pedido getPedidoDefault() {
        var pedido = new Pedido();
        pedido.setVendaSet(getVendasDefault());
        return pedido;
    }

    public Set<Venda> getVendasDefault() {
        var emprestimoFerramenta = Venda.builder()
                .valorTotalVenda(BigDecimal.TEN)
                .tipoVendaEnum(TipoVendaEnum.SERVICO)
                .descricaoVenda("Emprestimo de Ferramenta")
                .build();

        var ferramenta = Venda.builder()
                .valorTotalVenda(BigDecimal.TEN)
                .tipoVendaEnum(TipoVendaEnum.PRODUTO)
                .descricaoVenda("Ferramenta")
                .build();

        return Set.of(emprestimoFerramenta, ferramenta);
    }
}
