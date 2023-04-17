package it.unical.inf.ea.trintedapp.trintedapp.data.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utente")
@Data
@NoArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "via", column = @Column(name = "via_utente")),
        @AttributeOverride(name = "numeroCivico", column = @Column(name = "numeroCivico_utente")),
        @AttributeOverride(name = "citta", column = @Column(name = "citta_user"))
    })
    private Indirizzo indirizzo;

    

}
