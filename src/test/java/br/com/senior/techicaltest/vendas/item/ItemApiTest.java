package br.com.senior.techicaltest.vendas.item;

import br.com.senior.techicaltest.vendas.util.JsonUtil;
import org.hamcrest.core.Is;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ItemApiTest {

    @Mock
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST / Item / - Sucess")
    void save() throws Exception {
        var item = new Item();
        item.setDescricao("Computador");
        Mockito.doReturn(item).when(itemService).save(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders.post("/item")
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(JsonUtil.toJson(item)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.descricao", Is.is("Computador")));
    }

    @Test
    @DisplayName("GET / Item /1 - Sucess")
    void findOneSucess() throws Exception {
        var item = this.getItemDefault();
        Mockito.doReturn(item).when(itemService).findById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/item/{id}", item.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET / Item / - Sucess")
    void findAllSucess() throws Exception {
        var consumerList = itemService.findAll(PageRequest.of(1, 2));
        Mockito.when(itemService.findAll(PageRequest.of(1, 2))).thenReturn(consumerList);
        Mockito.doReturn(consumerList).when(itemService).findAll(PageRequest.of(1, 2));
        mockMvc.perform(MockMvcRequestBuilders.get("/item"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    private Item getItemDefault() {
        var item = new Item();
        item.setId(UUID.randomUUID());
        return item;
    }
}
