package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.item.Item;
import br.com.senior.techicaltest.vendas.item.ItemService;
import br.com.senior.techicaltest.vendas.pedido.dto.PedidoPersistDto;
import br.com.senior.techicaltest.vendas.venda.TipoVendaEnum;
import br.com.senior.techicaltest.vendas.venda.Venda;
import br.com.senior.techicaltest.vendas.venda.dto.VendaDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
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
        pedido.getVendaSet().forEach(venda -> venda.setPedido(pedido));
        return pedido;
    }

    private Map<String, Item> getAllItemsGroupedById(PedidoPersistDto pedidoPersistDto) {
        var hasSomeItem = pedidoPersistDto.getVendaDtoSet().stream().anyMatch(vendaDto -> Objects.nonNull(vendaDto.getItemId()));
        if (hasSomeItem) {
            var itemIdList = pedidoPersistDto.getVendaDtoSet()
                    .stream()
                    .filter(vendaDto -> TipoVendaEnum.PRODUTO.equals(vendaDto.getTipoVendaEnum()))
                    .map(VendaDto::getItemId)
                    .map(UUID::fromString)
                    .collect(Collectors.toList());

            return itemService.findAllByIdList(itemIdList)
                    .stream()
                    .collect(Collectors.toMap(item -> item.getId().toString(), item -> item));
        } else {
            return Collections.emptyMap();
        }
    }
}
