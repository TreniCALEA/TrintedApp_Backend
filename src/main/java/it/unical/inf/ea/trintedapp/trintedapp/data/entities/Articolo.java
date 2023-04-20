package it.unical.inf.ea.trintedapp.trintedapp.data.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Entity
@Table(name = "articolo")
@Data
@NoArgsConstructor
public class Articolo {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "titolo")
    private String titolo;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "prezzo")
    private Double prezzo;

    @ElementCollection
    @Column(name = "immagini")
    private ArrayList<Byte[]> immagini;

    @ManyToOne
    @JoinColumn(name = "utente", referencedColumnName = "id")
    private Utente utente;
}
