package it.unical.inf.ea.trintedapp.trintedapp.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unical.inf.ea.trintedapp.trintedapp.data.entities.Recensione;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecensioneDao extends JpaRepository<Long, Recensione> {

    @Query("select r.id" +
            "from Recensione r" +
            "where r.destinatario = :destinatario")
    List<Recensione> findByDestinatario(Long destinatario);

    
}
