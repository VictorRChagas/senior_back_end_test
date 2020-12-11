package br.com.senior.techicaltest.vendas.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
}
