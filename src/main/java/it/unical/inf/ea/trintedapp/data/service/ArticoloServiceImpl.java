package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.dao.ArticoloDao;
import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticoloServiceImpl implements ArticoloService {

    private final ArticoloDao articoloDao;
    private final UtenteDao utenteDao;
    private final ModelMapper modelMapper;

    @Override
    public void save(Articolo articolo) {
        articoloDao.save(articolo);
    }

    @Override
    public ArticoloDto save(ArticoloDto articoloDto) {
        Articolo articolo = modelMapper.map(articoloDto, Articolo.class);

        Optional<Utente> venditore = utenteDao.findById(articoloDto.getUtenteId());
        articolo.setUtente(venditore.get());

        Articolo a = articoloDao.save(articolo);
        return modelMapper.map(a, ArticoloDto.class);
    }

    @Override
    public Collection<Articolo> findAll(Specification<Articolo> spec) {
        return articoloDao.findAll(spec);
    }

    @Override
    public ArticoloDto getById(Long id) {
        Articolo articolo = articoloDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste un articolo con id: [%s]", id)));
        return modelMapper.map(articolo, ArticoloDto.class);
    }

    @Override
    public Collection<ArticoloDto> findAll() {
        return articoloDao.findAll().stream()
                .map(u -> modelMapper.map(u, ArticoloDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        articoloDao.deleteById(id);
    }

    @Override
    public Collection<ArticoloDto> getByTitoloContainingOrDescrizioneContaining(String titolo, String descrizione) {
        return articoloDao.findByTitoloContainingOrDescrizioneContaining(titolo, descrizione).stream()
                .map(u -> modelMapper.map(u, ArticoloDto.class))
                .collect(Collectors.toList());
    }

}
