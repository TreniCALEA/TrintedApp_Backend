package it.unical.inf.ea.trintedapp.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unical.inf.ea.trintedapp.data.entities.UtenteBannato;

@Repository
public interface UtenteBannatoDao extends JpaRepository<UtenteBannato, Long> {

}
