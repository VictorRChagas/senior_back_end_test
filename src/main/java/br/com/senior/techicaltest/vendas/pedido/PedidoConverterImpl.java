package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.item.Item;
import br.com.senior.techicaltest.vendas.item.ItemService;
import br.com.senior.techicaltest.vendas.pedido.dto.PedidoPersistDto;
import br.com.senior.techicaltest.vendas.venda.Venda;
import br.com.senior.techicaltest.vendas.venda.dto.VendaDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PedidoConverterImpl implements PedidoConverter {

    private final ItemService itemService;

    public PedidoConverterImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public Pedido mapToPedido(PedidoPersistDto pedidoPersistDto) {
        var pedido = new Pedido();
        var itemsMap = this.getAllItemsGroupedById(pedidoPersistDto);

        var vendaSet = pedidoPersistDto.getVendaDtoSet()
                .stream()
                .map(vendaDto -> new Venda(vendaDto, itemsMap.get(vendaDto.getItemId())))
                .collect(Collectors.toSet());

        pedido.setVendaSet(vendaSet);
        return pedido;
    }

    private Map<String, Item> getAllItemsGroupedById(PedidoPersistDto pedidoPersistDto) {
        var itemIdList = pedidoPersistDto.getVendaDtoSet()
                .stream().map(VendaDto::getItemId)
                .collect(Collectors.toList());

        return itemService.findAllByIdList(itemIdList)
                .stream()
                .collect(Collectors.toMap(item -> item.getId().toString(), item -> item));
    }
}
