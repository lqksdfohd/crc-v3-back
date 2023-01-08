package com.app.crc.services;

import com.app.crc.entites.Projet;
import com.app.crc.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    public Projet creerProjet(Projet projet){
        Optional<Projet> fromBase = projetRepository.findByNom(projet.getNom());
        if(fromBase.isPresent()){
            throw new IllegalArgumentException("projet déjà existant");
        }else{
            return projetRepository.save(projet);
        }
    }
}
