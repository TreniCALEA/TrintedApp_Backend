package it.unical.inf.ea.trintedapp.trintedapp.data.entities;

import java.util.List;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "autore", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Recensione> recensioniRicevute;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Articolo> articoli;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "username", column = @Column(name = "username")),
        @AttributeOverride(name = "password", column = @Column(name = "password")),
        @AttributeOverride(name = "email", column = @Column(name = "email"))
    })
    private Credenziali credenziali;

    @Column(name = "isAdmin")
    private Boolean isAdmin;

}
