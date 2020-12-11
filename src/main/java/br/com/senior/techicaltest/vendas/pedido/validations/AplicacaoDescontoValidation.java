package br.com.senior.techicaltest.vendas.pedido.validations;

import br.com.senior.techicaltest.vendas.pedido.model.Pedido;
import br.com.senior.techicaltest.vendas.pedido.PedidoService;
import br.com.senior.techicaltest.vendas.venda.Venda;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AplicacaoDescontoValidation implements PedidoValidations {

    private final PedidoService pedidoService;

    public AplicacaoDescontoValidation(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Override
    public void validate(Pedido pedido) {
        if (Objects.nonNull(pedido.getId())) {
            var pedidoFecthed = pedidoService.findById(pedido.getId());
            verifyStatusChangeContraint(pedidoFecthed, pedido);
            verifyDifferencesOnDisconts(pedidoFecthed, pedido);
        }
    }

    private void verifyDifferencesOnDisconts(Pedido pedidoSaved, Pedido pedido) {
        var vendasOnUpdateMap = pedido.getVendaSet()
                .stream()
                .collect(Collectors.toMap(Venda::getId, venda -> venda));

        var isSomeDescontoUpdated = pedidoSaved.getVendaSet().stream().anyMatch(venda -> {
            var vendaUpdated = vendasOnUpdateMap.get(venda.getId());
            return !venda.getValorDesconto().equals(vendaUpdated.getValorDesconto());
        });

        if (isSomeDescontoUpdated) {
            throw new UnsupportedOperationException("O desconto só pode ser alterado quando o pedido está aberto");
        }
    }

    private void verifyStatusChangeContraint(Pedido pedidoFecthed, Pedido pedido) {
        var statusHasChanged = pedidoFecthed.getStatusPedidoEnum().equals(pedido.getStatusPedidoEnum());

        if (statusHasChanged) {
            throw new UnsupportedOperationException("Não é possível alterar o status do pedido dessa forma");
        }
    }
}
