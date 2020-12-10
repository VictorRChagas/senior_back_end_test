package br.com.senior.techicaltest.vendas.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "message")
public class Error {
    private String message;
    private String documentation;

    public Error(String message) {
        this.message = message;
        this.documentation = "http://localhost:8080/documentation";
    }
}
