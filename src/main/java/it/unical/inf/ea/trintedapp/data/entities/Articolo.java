package it.unical.inf.ea.trintedapp.data.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

import it.unical.inf.ea.trintedapp.dto.Categoria;
import it.unical.inf.ea.trintedapp.dto.Condizioni;

@Entity
@Table(name = "articolo")
@Data
@NoArgsConstructor
public class Articolo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titolo")
    private String titolo;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "prezzo")
    private Double prezzo;

    @Lob
    @Column(name = "immagini")
    private ArrayList<String> immagini;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente")
    private Utente utente;

    @OneToOne(mappedBy = "articolo")
    private Ordine ordineAssociato;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "condizioni")
    private Condizioni condizioni;
}
