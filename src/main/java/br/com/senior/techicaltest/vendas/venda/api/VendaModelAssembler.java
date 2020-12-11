package br.com.senior.techicaltest.vendas.venda.api;

import br.com.senior.techicaltest.vendas.venda.Venda;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class VendaModelAssembler implements RepresentationModelAssembler<Venda, EntityModel<Venda>> {

    @Override
    public EntityModel<Venda> toModel(Venda entity) {
        return EntityModel.of(entity);
    }
}
