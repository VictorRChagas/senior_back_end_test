package br.com.senior.techicaltest.vendas.pedido;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PedidoTest {

    @Test
    void emptyConstructor() {
        Assertions.assertDoesNotThrow((ThrowingSupplier<Pedido>) Pedido::new);
    }

    @Test
    void createNewInstance() {
        var pedido = new Pedido();
        pedido.setValorTotalPedido(BigDecimal.TEN);
        pedido.setStatusPedidoEnum(StatusPedidoEnum.ABERTO);
        Assertions.assertEquals(BigDecimal.TEN, pedido.getValorTotalPedido());
        Assertions.assertEquals(StatusPedidoEnum.ABERTO, pedido.getStatusPedidoEnum());
    }

    @Test
    @DisplayName("throw exception for invalid description")
    void thorwsUnsuportedOperationExceptionIfDescricaoIsNull() {
        var pedidoDefault = getPedidoDefault();
        assertThrows(NullPointerException.class, () -> pedidoDefault.setStatusPedidoEnum(null));
    }

    @Test
    @DisplayName("throw exception for trying to remove status")
    void thorwsUnsuportedOperationExceptionIfStatusIsNull() {
        var pedidoDefault = getPedidoDefault();
        assertThrows(NullPointerException.class, () -> pedidoDefault.setValorTotalPedido(null));
    }

    private Pedido getPedidoDefault() {
        return Pedido.builder()
                .statusPedidoEnum(StatusPedidoEnum.ABERTO)
                .valorTotalPedido(BigDecimal.ZERO)
                .build();
    }
}
