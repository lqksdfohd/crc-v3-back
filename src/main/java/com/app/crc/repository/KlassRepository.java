package com.app.crc.repository;

import com.app.crc.entites.Klass;
import com.app.crc.entites.Projet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KlassRepository extends CrudRepository<Klass, Long> {
    Optional<Klass> findByProjetAndNom(Projet projet,String nom);
}
