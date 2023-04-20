package it.unical.inf.ea.trintedapp.trintedapp.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unical.inf.ea.trintedapp.trintedapp.data.entities.Ordine;

public interface OrdineDao extends JpaRepository<Long, Ordine>{
    
}
