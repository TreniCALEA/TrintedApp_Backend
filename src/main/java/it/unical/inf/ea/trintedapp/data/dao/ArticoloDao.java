package it.unical.inf.ea.trintedapp.data.dao;

import it.unical.inf.ea.trintedapp.data.entities.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticoloDao extends JpaRepository<Articolo, Long>, JpaSpecificationExecutor<Articolo> {
    List<Articolo> findByTitoloContainingOrDescrizioneContaining(String titolo, String descrizione);

    List<Articolo> findAllByPrezzo(Double prezzo);
}
