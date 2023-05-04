package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;

public interface OrdineService {
    
    void save(Ordine ordine);

    OrdineDto save(OrdineDto ordineDto);

    Collection<Ordine> findAll(Specification<Ordine> specification);

    OrdineDto getById(Long id);

    Collection<OrdineDto> findAll();

    void delete(Long id);
    
}
