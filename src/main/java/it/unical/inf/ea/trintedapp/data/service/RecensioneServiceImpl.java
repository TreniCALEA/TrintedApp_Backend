package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.dao.RecensioneDao;
import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.dto.RecensioneDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecensioneServiceImpl implements RecensioneService{

    private final RecensioneDao recensioneDao;

    private final ModelMapper modelMapper;

    @Override
    public void save(Recensione recensione) {
        recensioneDao.save(recensione);
    }

    @Override
    public RecensioneDto save(RecensioneDto recensioneDto) {
        Recensione recensione = modelMapper.map(recensioneDto, Recensione.class);
        Recensione recensione1 = recensioneDao.save(recensione);
        return modelMapper.map(recensione1, RecensioneDto.class);
    }

    @Override
    public List<Recensione> findAll(Long id) {
        Specification<Recensione> sp = recensioneDao.recensioneDestinatario(id);
        return recensioneDao.findAll(sp);
    }

    @Override
    public RecensioneDto getById(Long id) {
        Recensione recensione = recensioneDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste una recensione con id: [%s]", id)));
        return modelMapper.map(recensione, RecensioneDto.class);
    }

    @Override
    public Collection<RecensioneDto> findAll() {
        return recensioneDao.findAll().stream()
                .map(recensione -> modelMapper.map(recensione, RecensioneDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        recensioneDao.deleteById(id);
    }

    private static final int SIZE_FOR_PAGE = 20;

    @Override
    public Page<RecensioneDto> getAllPaged(int page) {
        Page<Recensione> recensioni = recensioneDao.findAll(PageRequest.of(page, SIZE_FOR_PAGE));
        List<RecensioneDto> listRecensioni = recensioni.stream().map(recensione -> modelMapper.map(recensione, RecensioneDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(listRecensioni);
    }

}

