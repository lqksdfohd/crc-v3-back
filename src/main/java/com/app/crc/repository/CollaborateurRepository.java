package com.app.crc.repository;

import com.app.crc.entites.Collaborateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborateurRepository  extends CrudRepository<Collaborateur, Long> {

    List<Collaborateur> findByPrincipalId(Long id);
    List<Collaborateur> findByCollaborantId(Long id);
}
