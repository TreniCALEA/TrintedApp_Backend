package it.unical.inf.ea.trintedapp.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unical.inf.ea.trintedapp.data.entities.Utente;

@Repository
public interface UtenteDao extends JpaRepository<Utente, Long>, JpaSpecificationExecutor<Utente> {

    List<Utente> findAllByRatingGeneraleOrderByRatingGenerale(Float ratingGenerale);

    Optional<Utente> findByCredenzialiUsername(String username);

    List<Utente> findAllByIsAdmin(Boolean isAdmin);

    @Query("from Utente u where u.credenziali.username like concat('%', :username, '%')")
    Page<Utente> getByUsernameLike(@Param("username") String credenzialiUsername, Pageable pageable);

    Optional<Utente> findByCredenzialiEmail(String credenzialiEmail);

}
