package it.unical.inf.ea.trintedapp.data.dao;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.unical.inf.ea.trintedapp.data.entities.Utente;

@Repository
public interface UtenteDao extends JpaRepository<Utente, Long>, JpaSpecificationExecutor<Utente> {

    List<Utente> findAllByRatingGeneraleOrderByRatingGenerale(Float ratingGenerale);

    Optional<Utente> findByCredenzialiUsername(String username);

    List<Utente> findAllByIsAdmin(Boolean isAdmin);

    Page<Utente> findAllByNomeLike(String nome, Pageable pageable);

}
