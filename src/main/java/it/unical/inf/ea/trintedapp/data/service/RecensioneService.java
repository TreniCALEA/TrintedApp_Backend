package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.dto.RecensioneDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.List;

public interface RecensioneService {

    void save(Recensione recensione);

    HttpStatus save(RecensioneDto recensionedDto, String jwt);

    List<Recensione> findAll(Long id);

    RecensioneDto getById(Long id);

    Collection<RecensioneDto> findAll();

    HttpStatus delete(Long id, String jwt);

    Page<RecensioneDto> getAllPaged(int page);
}
