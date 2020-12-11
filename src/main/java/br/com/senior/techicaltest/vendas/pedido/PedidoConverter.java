package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.pedido.dto.PedidoPersistDto;
import br.com.senior.techicaltest.vendas.pedido.model.Pedido;

public interface PedidoConverter {

    Pedido mapToPedido(PedidoPersistDto pedidoPersistDto);
}
