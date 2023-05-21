package it.unical.inf.ea.trintedapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class UtenteBasicDto {

    private Long id;

    private String immagine;

    @NotNull
    private String credenzialiUsername;

    private Float ratingGenerale;

}
