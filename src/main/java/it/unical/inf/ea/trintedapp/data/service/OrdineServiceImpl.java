package it.unical.inf.ea.trintedapp.data.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.unical.inf.ea.trintedapp.data.dao.ArticoloDao;
import it.unical.inf.ea.trintedapp.data.dao.OrdineDao;
import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import it.unical.inf.ea.trintedapp.data.entities.Indirizzo;
import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

    private final OrdineDao ordineDao;
    private final ArticoloDao articoloDao;
    private final UtenteDao utenteDao;

    private ModelMapper modelMapper;

    @Override
    public void save(Ordine ordine) {
        ordineDao.save(ordine);
    }

    @Override
    public OrdineDto save(OrdineDto ordineDto) {
        Ordine ordine = modelMapper.map(ordineDto, Ordine.class);
        return modelMapper.map(ordineDao.save(ordine), OrdineDto.class);
    }

    @Override
    public OrdineDto getById(Long id) {
        return modelMapper.map(
                ordineDao.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(String.format("Non esiste ordine con id: [%s]", id))),
                OrdineDto.class);
    }

    @Override
    public List<OrdineDto> getByAcquirente(Long id) {
        return ordineDao.findAllByAcquirente(id).stream()
                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdineDto> getByVenditore(Long id) {
        return ordineDao.findAllByVenditore(id).stream()
                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OrdineDto> findAll() {
        return ordineDao.findAll().stream()
                .map(ordine -> modelMapper.map(ordine, OrdineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        ordineDao.deleteById(id);
    }

    @Override
    @Transactional
    public void confirmOrder(Long acquirente, ArticoloDto articoloDto, Indirizzo indirizzo) {
        Utente _acquirente = utenteDao.findById(acquirente).get();
        Utente _venditore = utenteDao.findById(articoloDto.getUtenteId()).get();

        Articolo _articolo = articoloDao.findById(articoloDto.getId()).get();

        if (_acquirente.getSaldo().compareTo(articoloDto.getPrezzo()) > 1) {
            // create a new order in Ordine
            Ordine nuovoOrdine = new Ordine();
            nuovoOrdine.setAcquirente(_acquirente);
            nuovoOrdine.setVenditore(_venditore);
            nuovoOrdine.setArticolo(_articolo);
            nuovoOrdine.setDataAcquisto(LocalDate.now());
            nuovoOrdine.setIndirizzo(indirizzo);

            ordineDao.save(nuovoOrdine);

            // update Articolo
            _articolo.setAcquistabile(false);
            articoloDao.save(_articolo);

            // update buyer and seller balance
            _acquirente.setSaldo(_acquirente.getSaldo() - articoloDto.getPrezzo());
            _venditore.setSaldo(_venditore.getSaldo() + articoloDto.getPrezzo());

            utenteDao.save(_acquirente);
            utenteDao.save(_venditore);
        }
    }
}
