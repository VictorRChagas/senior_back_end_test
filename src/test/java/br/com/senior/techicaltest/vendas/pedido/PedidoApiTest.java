package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.pedido.dto.PedidoPersistDto;
import br.com.senior.techicaltest.vendas.pedido.model.Pedido;
import br.com.senior.techicaltest.vendas.util.JsonUtil;
import br.com.senior.techicaltest.vendas.venda.TipoVendaEnum;
import br.com.senior.techicaltest.vendas.venda.dto.VendaDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PedidoApiTest {

    @Mock
    private PedidoService pedidoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST / Pedido / - Sucess")
    void save() throws Exception {
        var pedidoPersistDto = getPedidoPersistDto();
        Mockito.doReturn(pedidoPersistDto).when(pedidoService).save(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders.post("/pedido")
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(JsonUtil.toJson(pedidoPersistDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    @DisplayName("GET / Pedido /1 - Sucess")
    void findOneSucess() throws Exception {
        var pedido = this.getPedidoDefault();
        Mockito.doReturn(pedido).when(pedidoService).findById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/pedido/{id}", pedido.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET / Pedido / - Sucess")
    void findAllSucess() throws Exception {
        var consumerList = pedidoService.findAll(PageRequest.of(1, 2));
        Mockito.when(pedidoService.findAll(PageRequest.of(1, 2))).thenReturn(consumerList);
        Mockito.doReturn(consumerList).when(pedidoService).findAll(PageRequest.of(1, 2));
        mockMvc.perform(MockMvcRequestBuilders.get("/pedido"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    public Pedido getPedidoDefault() {
        var pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        return pedido;
    }

    private PedidoPersistDto getPedidoPersistDto() {
        return new PedidoPersistDto(getPedidoVendaDtoSet());
    }

    public Set<VendaDto> getPedidoVendaDtoSet() {
        var servico = VendaDto.builder()
                .descricaoVenda("Troca de óleo")
                .valorTotalVenda(BigDecimal.TEN)
                .tipoVendaEnum(TipoVendaEnum.SERVICO)
                .build();

        return Set.of(servico);
    }
}
