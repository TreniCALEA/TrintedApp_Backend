package it.unical.inf.ea.trintedapp.trintedapp.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unical.inf.ea.trintedapp.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.trintedapp.data.entities.Utente;

@Repository
public interface UtenteDao extends JpaRepository<Long, Utente> {



}
