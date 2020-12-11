package br.com.senior.techicaltest.vendas.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemTest {

    @Test
    void emptyConstructor() {
        Assertions.assertDoesNotThrow((ThrowingSupplier<Item>) Item::new);
    }

    @Test
    void createNewInstance() {
        var item = new Item();
        item.setDescricao("Apple 12");
        Assertions.assertEquals("Apple 12", item.getDescricao());
    }

    @Test
    @DisplayName("throw exception for invalid description")
    void thorwsUnsuportedOperationExceptionIfDescricaoIsNull() {
        var defaultItem = getItemDefault();
        assertThrows(IllegalArgumentException.class, () -> defaultItem.setDescricao(null));
    }

    @Test
    @DisplayName("throw exception for trying to remove status")
    void thorwsUnsuportedOperationExceptionIfStatusIsNull() {
        var defaultItem = getItemDefault();
        assertThrows(NullPointerException.class, () -> defaultItem.setInativo(null));
    }

    private Item getItemDefault() {
        var item = new Item();
        item.setDescricao("Apple 12");
        return item;
    }

}
