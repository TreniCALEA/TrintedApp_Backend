package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.dao.ArticoloDao;
import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import it.unical.inf.ea.trintedapp.dto.ArticoloDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticoloServiceImpl implements ArticoloService {
    private final ArticoloDao articoloDao;

    private ModelMapper modelMapper;

    @Override
    public void save(Articolo articolo) {
        articoloDao.save(articolo);
    }

    @Override
    public ArticoloDto save(ArticoloDto articoloDto) {
        Articolo articolo = modelMapper.map(articoloDto, Articolo.class);
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

}
