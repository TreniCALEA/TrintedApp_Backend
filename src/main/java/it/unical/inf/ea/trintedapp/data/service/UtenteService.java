package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.UtenteBasicDto;
import it.unical.inf.ea.trintedapp.dto.UtenteCompletionDto;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import it.unical.inf.ea.trintedapp.dto.UtenteRegistrationDto;

public interface UtenteService {

    void save(Utente utente);

    UtenteRegistrationDto save(UtenteRegistrationDto utenteDto);

    Collection<UtenteBasicDto> findAll(Specification<Utente> spec);

    UtenteDto getById(Long id);

    Collection<UtenteBasicDto> findAll();

    HttpStatus delete(Long id, String jwt);

    Collection<UtenteBasicDto> getAllByUsernameLike(String credenzialiUsername);

    Page<UtenteBasicDto> getAllPaged(int page);

    Page<UtenteBasicDto> getAllByUsernameLike(String nome, int page);

    UtenteDto getByCredenzialiEmail(String credenzialiEmail);

    HttpStatus update(Long id, UtenteCompletionDto utenteDto, String jwt);

}
