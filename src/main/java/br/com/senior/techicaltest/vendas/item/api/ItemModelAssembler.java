package br.com.senior.techicaltest.vendas.item.api;

import br.com.senior.techicaltest.vendas.item.Item;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {

    @Override
    public EntityModel<Item> toModel(Item entity) {
        return EntityModel.of(entity);
    }
}
