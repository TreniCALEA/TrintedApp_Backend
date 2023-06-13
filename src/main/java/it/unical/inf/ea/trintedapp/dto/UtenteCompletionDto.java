package it.unical.inf.ea.trintedapp.dto;

import it.unical.inf.ea.trintedapp.data.entities.Indirizzo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UtenteCompletionDto {

    private String nome;

    private String cognome;

    private String immagine;

    private Indirizzo indirizzo;

}
