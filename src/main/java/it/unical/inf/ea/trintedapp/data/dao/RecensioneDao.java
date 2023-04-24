package it.unical.inf.ea.trintedapp.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.data.entities.Utente;

import java.util.List;

@Repository
public interface RecensioneDao extends JpaRepository<Recensione, Long> {
    List<Recensione> findAllByDestinatario(Utente destinatario);

    List<Recensione> findAllByAutore(Utente autore);

    List<Recensione> findAllByRating(Float rating);
}
