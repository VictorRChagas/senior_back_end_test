package br.com.senior.techicaltest.vendas.venda;

import br.com.senior.techicaltest.vendas.venda.dto.VendaPersistDto;

public interface VendaConverter {

    Venda mapToVenda(VendaPersistDto vendaPersistDto);

}
