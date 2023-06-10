package it.unical.inf.ea.trintedapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RecensioneDto {

    @NotNull
    private Long id;

    @NotNull
    private Float rating;

    private String commento;

    @NotNull
    private String autoreCredenzialiEmail;

    @NotNull
    private String destinatarioCredenzialiEmail;

}
