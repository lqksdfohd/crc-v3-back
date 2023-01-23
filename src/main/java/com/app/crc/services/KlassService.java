package com.app.crc.services;

import com.app.crc.entites.Collaborateur;
import com.app.crc.entites.Klass;
import com.app.crc.entites.Responsabilite;
import com.app.crc.repository.CollaborateurRepository;
import com.app.crc.repository.KlassRepository;
import com.app.crc.repository.ResponsabiliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KlassService {

    @Autowired
    private KlassRepository repository;

    @Autowired
    private CollaborateurRepository collaborateurRepo;

    @Autowired
    private ResponsabiliteRepository responsabiliteRepo;

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

    public Klass findById(Long id){
        Optional<Klass> fromBase = repository.findById(id);
        if(fromBase.isPresent()){
            return fromBase.get();
        }else{
            throw new IllegalArgumentException("aucune klass avec cet id n'existe en base");
        }
    }
}
