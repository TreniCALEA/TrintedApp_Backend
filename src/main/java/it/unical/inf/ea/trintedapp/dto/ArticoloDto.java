package it.unical.inf.ea.trintedapp.dto;

import java.util.ArrayList;

import it.unical.inf.ea.trintedapp.data.entities.Utente;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ArticoloDto {
    private Long id;

    private String titolo;

    private String descrizione;

    @NotNull
    private Double prezzo;

    private ArrayList<String> immagini;

    @NotNull
    private Long utenteId;

    private String categoria;

    private String condizioni;
}
