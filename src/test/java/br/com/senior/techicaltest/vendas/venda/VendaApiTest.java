package br.com.senior.techicaltest.vendas.venda;

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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class VendaApiTest {

    @Mock
    private VendaService vendaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / Venda /1 - Sucess")
    void findOneSucess() throws Exception {
        var venda = this.getVendaDefault();
        Mockito.doReturn(venda).when(vendaService).findById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/venda/{id}", venda.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET / Venda / - Sucess")
    void findAllSucess() throws Exception {
        var consumerList = vendaService.findAll(PageRequest.of(1, 2));
        Mockito.when(vendaService.findAll(PageRequest.of(1, 2))).thenReturn(consumerList);
        Mockito.doReturn(consumerList).when(vendaService).findAll(PageRequest.of(1, 2));
        mockMvc.perform(MockMvcRequestBuilders.get("/item"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    private Venda getVendaDefault() {
        var venda = new Venda();
        venda.setId(UUID.randomUUID());
        return venda;
    }
}
