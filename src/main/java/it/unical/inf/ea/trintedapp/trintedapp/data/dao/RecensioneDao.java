package it.unical.inf.ea.trintedapp.trintedapp.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unical.inf.ea.trintedapp.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.trintedapp.data.entities.Utente;

import java.util.List;

@Repository
public interface RecensioneDao extends JpaRepository<Long, Recensione> {


    List<Recensione> findAllByDestinatario(Utente destinatario);



}
