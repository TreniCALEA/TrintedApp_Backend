package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.Collection;

public interface ArticoloService {
    void save(Articolo articolo);

    ArticoloDto save(ArticoloDto articoloDto);

    Collection<Articolo> findAll(Specification<Articolo> spec);

    ArticoloDto getById(Long id);

    Collection<ArticoloDto> findAll();

    HttpStatus delete(Long id, String sessionId);

    Collection<ArticoloDto> getByTitoloContainingOrDescrizioneContaining(String titolo, String descrizione);

}
