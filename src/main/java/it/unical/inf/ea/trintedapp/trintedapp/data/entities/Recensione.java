package it.unical.inf.ea.trintedapp.trintedapp.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

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

    @Column(name = "destinatario")
    private Long destinatario;

    @ManyToOne
    @JoinColumn(name = "autore", referencedColumnName = "id")
    private Utente autore;


}
