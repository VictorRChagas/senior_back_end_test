package br.com.senior.techicaltest.vendas.item;

import br.com.senior.techicaltest.vendas.framework.CrudService;

import java.util.List;

public interface ItemService extends CrudService<Item, String> {

    List<Item> findAllByIdList(List<String> idList);
}
