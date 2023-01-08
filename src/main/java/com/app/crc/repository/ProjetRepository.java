package com.app.crc.repository;

import com.app.crc.entites.Projet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjetRepository  extends CrudRepository<Projet, Long> {

    public Optional<Projet> findByNom(String nom);
}
