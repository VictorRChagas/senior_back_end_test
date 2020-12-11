package br.com.senior.techicaltest.vendas.venda;

import br.com.senior.techicaltest.vendas.item.ItemService;
import br.com.senior.techicaltest.vendas.venda.dto.VendaPersistDto;
import org.springframework.stereotype.Service;

@Service
public class VendaConverterImpl implements VendaConverter {

    private  final ItemService itemService;

    public VendaConverterImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public Venda mapToVenda(VendaPersistDto vendaPersistDto) {
        var venda = Venda.builder()
                .descricaoVenda(vendaPersistDto.getDescricaoVenda())
                .tipoVendaEnum(vendaPersistDto.getTipoVendaEnum())
                .valorTotalVenda(vendaPersistDto.getValorTotalVenda())
                .build();

        vendaPersistDto.getItemId().ifPresent(id -> venda.setItem(itemService.findById(id)));
        return venda;
    }
}
