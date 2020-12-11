package br.com.senior.techicaltest.vendas.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "ITEM")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @NotBlank
    @Size(max = 120)
    @Column(name = "DESCRICAO", length = 120, nullable = false)
    private String descricao;

    @Column(name = "INATIVO", nullable = false)
    private Boolean inativo;

    public void setDescricao(String descricao) {
        if (Objects.isNull(descricao) || descricao.isBlank()) {
            throw new IllegalArgumentException("Descricao não deve ser nula ou vazia");
        }
        this.descricao = descricao;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = Objects.requireNonNull(inativo, "Status de atividade do item não pode ser nulo");
    }
}
