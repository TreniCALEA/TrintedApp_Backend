package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;

import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;

public interface OrdineService {
    
    void save(Ordine ordine);

    OrdineDto save(OrdineDto ordineDto);

    OrdineDto getById(Long id);

    Collection<OrdineDto> findAll();

    void delete(Long id);

    void confirmOrder(Long acquirente, ArticoloDto articoloDto);
    
}
