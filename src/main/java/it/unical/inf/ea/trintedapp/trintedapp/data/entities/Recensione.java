package it.unical.inf.ea.trintedapp.trintedapp.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recensione")
@Data
@NoArgsConstructor
public class Recensione {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "commento")
    private String commento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario")
    private Utente destinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autore")
    private Utente autore;

    @OneToOne(mappedBy = "recensioneOrdine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Ordine ordine;

}
