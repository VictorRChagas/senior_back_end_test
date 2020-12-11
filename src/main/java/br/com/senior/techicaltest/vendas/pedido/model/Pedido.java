package br.com.senior.techicaltest.vendas.pedido.model;

import br.com.senior.techicaltest.vendas.pedido.StatusPedidoEnum;
import br.com.senior.techicaltest.vendas.venda.Venda;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "PEDIDO")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @NotNull
    @Column(name = "VALOR_TOTAL_PEDIDO")
    private BigDecimal valorTotalPedido;

    @Column(name = "STATUS")
    private StatusPedidoEnum statusPedidoEnum;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Venda> vendaSet = new HashSet<>();

    public void setValorTotalPedido(@NotNull BigDecimal valorTotalPedido) {
        this.valorTotalPedido = Objects.requireNonNull(valorTotalPedido, "Valor total do pedido não deve ser nulo");
    }

    public void setStatusPedidoEnum(StatusPedidoEnum statusPedidoEnum) {
        this.statusPedidoEnum = Objects.requireNonNull(statusPedidoEnum, "Status do pedido não pode ser nulo");
    }

    public void setVendaSet(Set<Venda> vendaSet) {
        this.vendaSet = vendaSet;
    }

    /***
     * used basically for tests
     */
    public void setId(UUID id) {
        if (Objects.nonNull(this.id) && !this.id.equals(id)) {
            throw new UnsupportedOperationException("Não pode mudar o id do pedido");
        }
        this.id = Objects.requireNonNull(id, "Não pode setar o id de um pedido existente para nulo");
    }
}
