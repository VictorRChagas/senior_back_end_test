package br.com.senior.techicaltest.vendas.venda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("GET /vendaService/1 - Sucess")
    void findOneSucess() throws Exception {
        var venda = this.getVendaDefault();
        Mockito.doReturn(vendaService).when(vendaService).findById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/venda/{id}", 1))
                .andExpect(status().isOk());
    }

    private Venda getVendaDefault() {
        return new Venda();
    }
}
