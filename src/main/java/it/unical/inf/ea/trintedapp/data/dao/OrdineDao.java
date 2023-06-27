package it.unical.inf.ea.trintedapp.data.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import it.unical.inf.ea.trintedapp.data.entities.Utente;

import org.springframework.stereotype.Repository;

@Repository
public interface OrdineDao extends JpaRepository<Ordine, Long> {

    Optional<Ordine> findAllByAcquirente(Long id);

    Optional<Ordine> findAllByVenditore(Long id);

    List<Ordine> findAllByAcquirenteAndDataAcquistoBetween(Utente Acquirente, LocalDate from, LocalDate to);

}
