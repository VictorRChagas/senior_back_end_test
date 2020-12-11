package br.com.senior.techicaltest.vendas.item;

import br.com.senior.techicaltest.vendas.framework.CrudServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl extends CrudServiceImpl<Item, String> implements ItemService {

    private final ItemRepository repository;

    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Item, String> getRepository() {
        return repository;
    }

    @Override
    public List<Item> findAllByIdList(List<String> idList) {
        return repository.findAllById(idList);
    }
}
