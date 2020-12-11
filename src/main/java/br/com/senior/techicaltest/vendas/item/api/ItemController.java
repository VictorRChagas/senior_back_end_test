package br.com.senior.techicaltest.vendas.item.api;

import br.com.senior.techicaltest.vendas.framework.CrudRestController;
import br.com.senior.techicaltest.vendas.framework.CrudService;
import br.com.senior.techicaltest.vendas.item.Item;
import br.com.senior.techicaltest.vendas.item.dto.ItemPersistDto;
import br.com.senior.techicaltest.vendas.item.ItemService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController extends CrudRestController<Item, String, ItemPersistDto> {

    private final ItemService itemService;
    private final ItemModelAssembler itemModelAssembler;

    public ItemController(ItemService itemService, ItemModelAssembler itemModelAssembler) {
        this.itemService = itemService;
        this.itemModelAssembler = itemModelAssembler;
    }

    @Override
    public CrudService<Item, String> getService() {
        return itemService;
    }

    @Override
    public RepresentationModelAssembler<Item, EntityModel<Item>> getRepresentationModelAssembler() {
        return itemModelAssembler;
    }
}
