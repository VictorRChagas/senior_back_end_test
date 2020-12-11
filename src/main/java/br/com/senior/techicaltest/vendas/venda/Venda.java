package br.com.senior.techicaltest.vendas.venda;

import br.com.senior.techicaltest.vendas.item.Item;
import br.com.senior.techicaltest.vendas.pedido.Pedido;
import br.com.senior.techicaltest.vendas.venda.dto.VendaDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "VENDA")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @NotNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido pedido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_VENDA", length = 120, nullable = false)
    private TipoVendaEnum tipoVendaEnum;

    @NotNull
    @Size(max = 120)
    @Column(name = "VALOR_TOTAL_VENDA", length = 120, nullable = false)
    private BigDecimal valorTotalVenda;

    @Column(name = "DESCRICAO_VENDA", length = 120)
    private String descricaoVenda;

    @Column(name = "VALOR_DESCONTO")
    private BigDecimal valorDesconto;

    @OneToOne
    @JoinColumn(name = "ID_ITEM")
    private Item item;

    public Venda(String descricaoVenda) {
        this.descricaoVenda = descricaoVenda;
    }

    public Venda(VendaDto vendaDto, Item item) {
        setItem(item);
        setTipoVendaEnum(vendaDto.getTipoVendaEnum());
        setDescricaoVenda(vendaDto.getDescricaoVenda());
        setValorTotalVenda(vendaDto.getValorTotalVenda());
        setValorDesconto(vendaDto.getValorDesconto());
    }

    public void setValorTotalVenda(@NotNull BigDecimal valorTotalVenda) {
        this.valorTotalVenda = Objects.requireNonNull(valorTotalVenda, "Valor total não deve ser nulo");
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setTipoVendaEnum(@NotNull TipoVendaEnum tipoVendaEnum) {
        this.tipoVendaEnum = Objects.requireNonNull(tipoVendaEnum, "Tipo da Venda não deve ser nulo");
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        if (TipoVendaEnum.SERVICO.equals(tipoVendaEnum)) {
            throw new UnsupportedOperationException("Não é possível aplicar descontos nos serviços");
        }
        this.valorDesconto = valorDesconto;
    }
}
