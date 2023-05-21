package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.UtenteBasicDto;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteDao utenteDao;

    private final ModelMapper modelMapper;

    @Override
    public void save(Utente utente) {
        utenteDao.save(utente);
    }

    @Override
    public UtenteDto save(UtenteDto utenteDto) {
        Utente utente = modelMapper.map(utenteDto, Utente.class);
        Utente u = utenteDao.save(utente);
        return modelMapper.map(u, UtenteDto.class);
    }

    @Override
    public Collection<UtenteBasicDto> findAll(Specification<Utente> spec) {
        return utenteDao.findAll(spec).stream()
                        .map(u -> modelMapper.map(u, UtenteBasicDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    public UtenteDto getById(Long id) {
        Utente utente = utenteDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste un utente con id: [%s]", id)));
        return modelMapper.map(utente, UtenteDto.class);
    }

    @Override
    public Collection<UtenteBasicDto> findAll() {
        return utenteDao.findAll().stream()
                .map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        utenteDao.deleteById(id);
    }

    private static final int SIZE_FOR_PAGE = 20;

    @Override
    public Page<UtenteBasicDto> getAllPaged(int page) {
        Page<Utente> utenti = utenteDao.findAll(PageRequest.of(page, SIZE_FOR_PAGE));
        List<UtenteBasicDto> listUtenti = utenti.stream().map(u -> modelMapper.map(u, UtenteBasicDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(listUtenti);
    }

    @Override
    public Page<UtenteBasicDto> getAllByNomeLike(String nome, int page) {
        PageRequest pageRequest = PageRequest.of(page, SIZE_FOR_PAGE, Sort.by("nome").ascending());
        List<UtenteDto> list = utenteDao.findAllByNomeLike(nome, pageRequest).stream()
                .map(u -> modelMapper.map(u, UtenteDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(list);
    }

}
