package br.com.senior.techicaltest.vendas.item.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ItemPersistDto {

    @NotNull
    @NotBlank
    private String descricao;

}
