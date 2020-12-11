package br.com.senior.techicaltest.vendas.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemApiTest {

    @Mock
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /item/1 - Sucess")
    void findOneSucess() throws Exception {
        var item = this.getItemDefault();
        Mockito.doReturn(item).when(itemService).findById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/item/{id}", 1))
                .andExpect(status().isOk());
    }

    private Item getItemDefault() {
        return new Item();
    }
}
