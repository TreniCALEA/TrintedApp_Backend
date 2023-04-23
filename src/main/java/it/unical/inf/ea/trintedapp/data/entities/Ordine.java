package it.unical.inf.ea.trintedapp.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ordine")
@Data
@NoArgsConstructor
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acquirente")
    private Utente acquirente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venditore")
    private Utente venditore;

    @Column(name = "data_acquisto")
    private LocalDate dataAcquisto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articolo")
    private Articolo articolo;

    @Column(name = "prezzo_finale")
    private Long prezzoFinale;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recensione_ordine")
    private Recensione recensioneOrdine;
}
