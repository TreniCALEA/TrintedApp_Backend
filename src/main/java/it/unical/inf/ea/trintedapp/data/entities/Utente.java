package it.unical.inf.ea.trintedapp.data.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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

        @Lob
        @Column(name = "immagineProfilo")
        private String immagine;

        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "via", column = @Column(name = "via_utente")),
                        @AttributeOverride(name = "numero_civico", column = @Column(name = "numeroCivico_utente")),
                        @AttributeOverride(name = "citta", column = @Column(name = "citta_utente"))
        })
        private Indirizzo indirizzo;

        @JsonManagedReference
        @OneToMany(mappedBy = "autore", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
        private List<Recensione> recensioniRicevute;

        @JsonManagedReference
        @OneToMany(mappedBy = "utente", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
        private List<Articolo> articoli;

        @Embedded
        @AttributeOverrides({
                        @AttributeOverride(name = "username", column = @Column(name = "username", unique = true)),
                        @AttributeOverride(name = "password", column = @Column(name = "password")),
                        @AttributeOverride(name = "email", column = @Column(name = "email"))
        })
        private Credenziali credenziali;

        @Column(name = "isAdmin")
        private Boolean isAdmin;

        @JsonManagedReference
        @OneToMany(mappedBy = "acquirente", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
        private List<Ordine> ordiniAcquirente;

        @JsonManagedReference
        @OneToMany(mappedBy = "venditore", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
        private List<Ordine> ordiniVenditore;

        @Column(name = "isOwner")
        private Boolean isOwner;

        @Column(name = "rating_genrale")
        private Float ratingGenerale;

}
