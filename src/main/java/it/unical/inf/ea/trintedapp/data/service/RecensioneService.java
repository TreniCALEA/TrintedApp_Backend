package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.dto.RecensioneDto;

import java.util.Collection;

public interface RecensioneService {

    void save(Recensione recensione);

    RecensioneDto save(RecensioneDto recensioneDto);

    Collection<Recensione> findAll(Long id);

    RecensioneDto getById(Long id);

    Collection<RecensioneDto> findAll();

    void delete(Long id);
}
