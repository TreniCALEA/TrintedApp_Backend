package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface RecensioneService {

    void saveOrUpdate(Recensione recensione);

    Collection<Recensione> findAll(Specification<Recensione> rspec);

    void delete(Long id);
}