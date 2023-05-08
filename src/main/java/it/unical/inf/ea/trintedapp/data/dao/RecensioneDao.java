package it.unical.inf.ea.trintedapp.data.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.data.entities.Utente;

import java.util.List;

@Repository
public interface RecensioneDao extends JpaRepository<Recensione, Long>, JpaSpecificationExecutor<Recensione> {
    List<Recensione> findAllByDestinatario(Utente destinatario);

    List<Recensione> findAllByAutore(Utente autore);

    List<Recensione> findAllByRating(Float rating);

    default Specification<Recensione> recensioneDestinatario (Long id){
        return new Specification<Recensione>() {
            @Override
            public Predicate toPredicate(Root<Recensione> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("destinatario"), "%"+id+"%");
            }
        };
    }

}
