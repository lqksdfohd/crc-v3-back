package com.app.crc.services;

import com.app.crc.dtos.KlassDto;
import com.app.crc.entites.Klass;
import com.app.crc.entites.Projet;
import com.app.crc.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    public List<Projet> recupererTousLesProjets(){
        Iterable<Projet> iterable = projetRepository.findAll();
        List<Projet> output = new ArrayList<>();
        iterable.forEach(p -> output.add(p));
        return output;
    }

    public Projet recupererUnProjetParId(Long id){
        Optional<Projet> optional = projetRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }else{
            throw new IllegalArgumentException("aucun projet ne correspond à cet id");
        }
    }
}
