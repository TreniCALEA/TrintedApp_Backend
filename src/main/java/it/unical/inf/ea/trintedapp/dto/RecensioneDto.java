package it.unical.inf.ea.trintedapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RecensioneDto {

    private Long id;

    private Float rating;

    @NotNull
    private String commento;

}
