package it.unical.inf.ea.trintedapp.data.service;

import it.unical.inf.ea.trintedapp.data.dao.RecensioneDao;
import it.unical.inf.ea.trintedapp.data.entities.Recensione;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RecensioneServiceImpl implements RecensioneService{

    private  final RecensioneDao recensioneDao;

    @Override
    public void saveOrUpdate(Recensione recensione) {
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
}

