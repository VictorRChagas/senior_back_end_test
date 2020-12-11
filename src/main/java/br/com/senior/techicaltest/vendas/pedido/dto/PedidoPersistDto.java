package br.com.senior.techicaltest.vendas.pedido.dto;

import br.com.senior.techicaltest.vendas.venda.dto.VendaDto;
import lombok.Getter;

import java.util.Set;

@Getter
public class PedidoPersistDto {

    private Set<VendaDto> vendaDtoSet;
}
