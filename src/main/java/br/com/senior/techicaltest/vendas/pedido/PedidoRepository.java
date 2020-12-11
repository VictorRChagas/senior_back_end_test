package br.com.senior.techicaltest.vendas.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Transactional
    @Modifying
    @Query("update Pedido p set p.statusPedidoEnum = 'APROVADO' where p.id =  ?1")
    int approveOrder(String pedidoId);
}
