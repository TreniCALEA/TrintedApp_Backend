package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.dto.RecensioneDto;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface RecensioneService {

    void save(Recensione recensione);

    RecensioneDto save(RecensioneDto recensioneDto);

    List<Recensione> findAll(Long id);

    RecensioneDto getById(Long id);

    Collection<RecensioneDto> findAll();

    void delete(Long id);

    Page<RecensioneDto> getAllPaged(int page);
}
