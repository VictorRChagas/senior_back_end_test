package br.com.senior.techicaltest.vendas.venda.dto;

import br.com.senior.techicaltest.vendas.venda.TipoVendaEnum;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Getter
public class VendaPersistDto {

    @NotNull
    private TipoVendaEnum tipoVendaEnum;

    @NotNull
    private BigDecimal valorTotalVenda;

    private Optional<String> itemId;

    private String descricaoVenda;

    public Optional<String> getItemId() {
        return Objects.isNull(itemId) ? Optional.empty() : itemId;
    }
}
