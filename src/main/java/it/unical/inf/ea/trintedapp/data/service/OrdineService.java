package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;

import it.unical.inf.ea.trintedapp.data.entities.Indirizzo;
import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;

public interface OrdineService {

    void save(Ordine ordine);

    OrdineDto save(OrdineDto ordineDto);

    OrdineDto getById(Long id);

    Collection<OrdineDto> findAll();

    void delete(Long id);

    HttpStatus confirmOrder(Long acquirente, Long articoloId, Indirizzo indirizzo, String jwt);

    List<OrdineDto> getByAcquirente(Long id);

    List<OrdineDto> getByVenditore(Long id);

}
