package br.com.senior.techicaltest.vendas.pedido.validations;

import br.com.senior.techicaltest.vendas.pedido.Pedido;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InatividadePedidoValidation implements PedidoValidations {

    @Override
    public void validate(Pedido pedido) {
        var invalidItemFound = pedido.getVendaSet().stream()
                .filter(venda -> Objects.nonNull(venda.getItem()) && venda.getItem().getInativo())
                .findFirst();

        if (invalidItemFound.isPresent()) {
            throw new UnsupportedOperationException("Não é possível adicioanr itens inativos no pedido!");
        }
    }
}
