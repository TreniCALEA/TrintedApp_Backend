package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.dao.RecensioneDao;
import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import it.unical.inf.ea.trintedapp.dto.RecensioneDto;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public Collection<Recensione> findAll(Long id) {
        Specification<Recensione> sp = recensioneDao.recensioneDestinatario(id);
        return recensioneDao.findAll(sp);
    }

    @Override
    public void delete(Long id) {
        recensioneDao.deleteById(id);
    }
    
    @Override
    public RecensioneDto save(RecensioneDto recensioneDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public RecensioneDto getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Collection<RecensioneDto> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}

