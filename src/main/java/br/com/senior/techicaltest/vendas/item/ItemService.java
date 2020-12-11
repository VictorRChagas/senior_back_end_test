package br.com.senior.techicaltest.vendas.item;

import br.com.senior.techicaltest.vendas.framework.CrudService;

import java.util.List;
import java.util.UUID;

public interface ItemService extends CrudService<Item, UUID> {

    List<Item> findAllByIdList(List<UUID> idList);

    Boolean inativarItem(String itemId);
}
