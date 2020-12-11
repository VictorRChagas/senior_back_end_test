package br.com.senior.techicaltest.vendas.item;

import br.com.senior.techicaltest.vendas.framework.CrudServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl extends CrudServiceImpl<Item, UUID> implements ItemService {

    private final ItemRepository repository;

    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Item, UUID> getRepository() {
        return repository;
    }

    @Override
    public List<Item> findAllByIdList(List<UUID> idList) {
        return repository.findAllById(idList);
    }

    @Override
    public Boolean inativarItem(String itemId) {
        return repository.inativarItem(itemId) == 1;
    }
}
