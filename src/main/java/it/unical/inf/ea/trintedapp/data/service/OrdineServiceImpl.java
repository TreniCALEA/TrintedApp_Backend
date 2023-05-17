package it.unical.inf.ea.trintedapp.data.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.unical.inf.ea.trintedapp.data.dao.OrdineDao;
import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import it.unical.inf.ea.trintedapp.dto.OrdineDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService{

    private final OrdineDao ordineDao;
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
            ordineDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Non esiste ordine con id: [%s]", id))),
            OrdineDto.class
        );
    }

    @Override
    public Collection<OrdineDto> findAll() {
        return ordineDao.findAll().stream()
            .map(ord -> modelMapper.map(ord, OrdineDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        ordineDao.deleteById(id);
    }
    
}
