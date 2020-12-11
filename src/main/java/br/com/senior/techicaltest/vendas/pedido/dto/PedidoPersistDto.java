package br.com.senior.techicaltest.vendas.pedido.dto;

import br.com.senior.techicaltest.vendas.venda.dto.VendaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPersistDto {

    private Set<VendaDto> vendaDtoSet;
}
