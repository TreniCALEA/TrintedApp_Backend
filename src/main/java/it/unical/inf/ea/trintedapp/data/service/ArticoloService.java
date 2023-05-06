package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface ArticoloService {
    void save(Articolo articolo);

    ArticoloDto save(ArticoloDto articoloDto);

    Collection<Articolo> findAll(Specification<Articolo> spec);

    ArticoloDto getById(Long id);

    Collection<ArticoloDto> findAll();

    void delete(Long id);
}
