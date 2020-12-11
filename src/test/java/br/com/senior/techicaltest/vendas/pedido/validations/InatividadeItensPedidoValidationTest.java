package br.com.senior.techicaltest.vendas.pedido.validations;

import br.com.senior.techicaltest.vendas.item.Item;
import br.com.senior.techicaltest.vendas.pedido.Pedido;
import br.com.senior.techicaltest.vendas.pedido.StatusPedidoEnum;
import br.com.senior.techicaltest.vendas.venda.TipoVendaEnum;
import br.com.senior.techicaltest.vendas.venda.Venda;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InatividadeItensPedidoValidationTest {

    private InatividadeItensPedidoValidation InatividadeItensPedidoValidation = new InatividadeItensPedidoValidation();

    @Test
    public void mustThrowExceptionForUnactiveItems() {
        var pedido = getPedidoDefault();
        assertThrows(UnsupportedOperationException.class, () -> InatividadeItensPedidoValidation.validate(pedido));
    }

    @Test
    public void doesNotThrowExceptionForactiveItems() {
        var pedido = getPedidoDefault();
        var itensAtivos = pedido.getVendaSet()
                .stream()
                .filter(venda -> !venda.getItem().getInativo())
                .collect(Collectors.toSet());
        pedido.setVendaSet(itensAtivos);
        assertDoesNotThrow(() -> InatividadeItensPedidoValidation.validate(pedido));
    }

    private Pedido getPedidoDefault() {
        var pedido = Pedido.builder()
                .statusPedidoEnum(StatusPedidoEnum.ABERTO)
                .valorTotalPedido(BigDecimal.valueOf(100))
                .vendaSet(this.getVendaSet())
                .build();
        return pedido;
    }

    private Set<Venda> getVendaSet() {
        var item = new Item("Celular");
        item.setInativo(true);
        var venda1 = Venda.builder()
                .valorDesconto(BigDecimal.valueOf(20))
                .valorTotalVenda(BigDecimal.valueOf(800))
                .tipoVendaEnum(TipoVendaEnum.PRODUTO)
                .item(item)
                .build();

        var venda2 = Venda.builder()
                .valorDesconto(BigDecimal.valueOf(10))
                .valorTotalVenda(BigDecimal.valueOf(100))
                .tipoVendaEnum(TipoVendaEnum.PRODUTO)
                .item(new Item("Telefone fixo"))
                .build();

        return Set.of(venda1, venda2);
    }
}
