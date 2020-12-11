package br.com.senior.techicaltest.vendas.venda;

import br.com.senior.techicaltest.vendas.pedido.Pedido;
import br.com.senior.techicaltest.vendas.pedido.StatusPedidoEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VendaTest {

    @Test
    void emptyConstructor() {
        Assertions.assertDoesNotThrow((ThrowingSupplier<Venda>) Venda::new);
    }

    @Test
    void createNewInstance() {
        var venda = new Venda();
        venda.setTipoVendaEnum(TipoVendaEnum.SERVICO);
        Assertions.assertEquals(TipoVendaEnum.SERVICO, venda.getTipoVendaEnum());
    }

    @Test
    @DisplayName("throw exception for invalid valor venda")
    void thorwsUnsuportedOperationExceptionIfValorPedidoIsNull() {
        var defaultPedido = getDefaultPedido();
        assertThrows(NullPointerException.class, () -> defaultPedido.setValorTotalPedido(null));
    }

    @Test
    @DisplayName("throw exception for trying to remove status")
    void thorwsUnsuportedOperationExceptionIfStatusPedidoIsNull() {
        var defaultPedido = getDefaultPedido();
        assertThrows(NullPointerException.class, () -> defaultPedido.setValorTotalPedido(null));
    }

    private Pedido getDefaultPedido() {
        return Pedido.builder()
                .statusPedidoEnum(StatusPedidoEnum.APROVADO)
                .build();
    }
}
