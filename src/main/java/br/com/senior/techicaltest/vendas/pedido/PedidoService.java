package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.framework.CrudService;
import br.com.senior.techicaltest.vendas.pedido.model.Pedido;

import java.util.UUID;

public interface PedidoService extends CrudService<Pedido, UUID> {

    Boolean approvePedido(String pedidoId);
}
