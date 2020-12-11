package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.pedido.dto.PedidoPersistDto;

public interface PedidoConverter {

    Pedido mapToPedido(PedidoPersistDto pedidoPersistDto);
}
