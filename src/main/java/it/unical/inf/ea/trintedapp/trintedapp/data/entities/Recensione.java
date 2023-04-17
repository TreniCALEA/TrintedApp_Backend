package it.unical.inf.ea.trintedapp.trintedapp.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@NoArgsConstructor
public class Recensione {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "commento")
    private Blob commento;

    @ManyToOne
    @JoinColumn(name = "autore", referencedColumnName = "id")
    private Utente autore;


}
