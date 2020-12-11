package br.com.senior.techicaltest.vendas.venda.dto;

import br.com.senior.techicaltest.vendas.venda.TipoVendaEnum;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class VendaDto {

    private TipoVendaEnum tipoVendaEnum;
    private BigDecimal valorTotalVenda;
    private String descricaoVenda;
    private String itemId;
    private BigDecimal valorDesconto;

}
