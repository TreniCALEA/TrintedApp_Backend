package it.unical.inf.ea.trintedapp.dto;

import it.unical.inf.ea.trintedapp.data.entities.Indirizzo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UtenteDto {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String cognome;

    @Email
    private String credenzialiEmail;

    private String credenzialiUsername;

    private Boolean isAdmin;

    private Boolean isOwner;

    private Float ratingGenerale;

    private String immagine;

    private Indirizzo indirizzo;

}
