package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;

public interface UtenteService {

    void save(Utente utente);

    UtenteDto save(UtenteDto utenteDto);

    Collection<Utente> findAll(Specification<Utente> spec);

    UtenteDto getById(Long id);

    Collection<UtenteDto> findAll();

    void delete(Long id);

}
