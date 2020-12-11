package br.com.senior.techicaltest.vendas.pedido.api;

import br.com.senior.techicaltest.vendas.pedido.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido entity) {
        return EntityModel.of(entity);
    }
}
