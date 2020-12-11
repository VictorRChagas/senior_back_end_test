package br.com.senior.techicaltest.vendas.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Transactional
    @Modifying
    @Query("update Item i set i.inativo = true where i.id =  ?1")
    int inativarItem(String id);

}
