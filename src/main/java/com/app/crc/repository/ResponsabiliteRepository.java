package com.app.crc.repository;

import com.app.crc.entites.Responsabilite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsabiliteRepository extends CrudRepository<Responsabilite, Long> {

    @Query("SELECT r FROM Responsabilite r WHERE r.klass.id = ?1")
    List<Responsabilite> findByKlassId(Long id);
}
