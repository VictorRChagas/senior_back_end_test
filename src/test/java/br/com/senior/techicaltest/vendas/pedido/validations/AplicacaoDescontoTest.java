package br.com.senior.techicaltest.vendas.pedido.validations;

import br.com.senior.techicaltest.vendas.pedido.PedidoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AplicacaoDescontoTest {

    @Mock
    private PedidoService pedidoService;
}
