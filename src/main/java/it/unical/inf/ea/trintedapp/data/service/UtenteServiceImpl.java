package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.unical.inf.ea.trintedapp.data.dao.UtenteDao;
import it.unical.inf.ea.trintedapp.data.entities.Utente;
import it.unical.inf.ea.trintedapp.dto.UtenteDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteDao utenteDao;

    private ModelMapper modelMapper;

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
    public Collection<Utente> findAll(Specification<Utente> spec) {
        return utenteDao.findAll(spec);
    }

    @Override
    public UtenteDto getById(Long id) {
        Utente utente = utenteDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste un utente con id: [%s]", id)));
        return modelMapper.map(utente, UtenteDto.class);
    }

    @Override
    public Collection<UtenteDto> findAll() {
        return utenteDao.findAll().stream()
                        .map(u -> modelMapper.map(u, UtenteDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        utenteDao.deleteById(id);
    }

}
