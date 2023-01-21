package com.app.crc.services;

import com.app.crc.entites.Klass;
import com.app.crc.repository.KlassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KlassService {

    @Autowired
    private KlassRepository repository;

    public Klass sauvegarderUneKlass(Klass klass){
        Optional<Klass> optional = repository.findByProjetAndNom(klass.getProjet(), klass.getNom());
        if(optional.isPresent()){
            throw new IllegalArgumentException("il existe déjà une Klass de ce nom");
        }else{
            return repository.save(klass);
        }
    }

    public void supprimerUneKlass(long id){
        Optional<Klass> fromBase = repository.findById(id);
        if(fromBase.isPresent()){
            repository.delete(fromBase.get());
        }else{
            throw new IllegalArgumentException("aucune klass avec cet id n'existe en base");
        }
    }
}
