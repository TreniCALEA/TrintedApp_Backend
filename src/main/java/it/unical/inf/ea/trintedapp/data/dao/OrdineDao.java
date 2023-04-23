package it.unical.inf.ea.trintedapp.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unical.inf.ea.trintedapp.data.entities.Ordine;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineDao extends JpaRepository<Ordine, Long>{
    
}
