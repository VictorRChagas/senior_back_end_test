package br.com.senior.techicaltest.vendas.item.api;

import br.com.senior.techicaltest.vendas.framework.CrudRestController;
import br.com.senior.techicaltest.vendas.framework.CrudService;
import br.com.senior.techicaltest.vendas.item.Item;
import br.com.senior.techicaltest.vendas.item.ItemService;
import br.com.senior.techicaltest.vendas.item.dto.ItemPersistDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/item")
public class ItemController extends CrudRestController<Item, UUID, ItemPersistDto> {

    private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final ItemModelAssembler itemModelAssembler;

    public ItemController(ItemService itemService, ItemModelAssembler itemModelAssembler) {
        this.itemService = itemService;
        this.itemModelAssembler = itemModelAssembler;
    }

    @Override
    public CrudService<Item, UUID> getService() {
        return itemService;
    }

    @Override
    public RepresentationModelAssembler<Item, EntityModel<Item>> getRepresentationModelAssembler() {
        return itemModelAssembler;
    }

    @PostMapping("inativar/{id}")
    public ResponseEntity<Boolean> inativarItem(@PathVariable("id") String itemId) {
        LOGGER.debug("Inativando item id: {}", itemId);
        return ResponseEntity.ok(itemService.inativarItem(itemId));
    }
}
