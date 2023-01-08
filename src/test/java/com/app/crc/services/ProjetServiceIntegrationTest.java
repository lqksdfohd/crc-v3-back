package com.app.crc.services;

import com.app.crc.entites.Projet;
import com.app.crc.repository.ProjetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProjetServiceIntegrationTest {

    @Autowired
    private ProjetService projetService;

    @Autowired
    private ProjetRepository projetRepository;

    //créer nouveau projet
    @Test
    public void testCreerProjet(){
        Projet projet = new Projet();
        projet.setNom("nouveau");

        Projet resultat = projetService.creerProjet(projet);

        Optional<Projet> fromBase = projetRepository.findByNom(projet.getNom());

        Assertions.assertEquals(resultat, fromBase.get());

    }

    @Test
    public void testCreerProjet_projetDejaExistant(){
        Projet projet = new Projet();
        projet.setNom("projet existant");

        Projet resultat = projetService.creerProjet(projet);

        Optional<Projet> fromBase = projetRepository.findByNom(projet.getNom());

        Assertions.assertEquals(resultat, fromBase.get());
        Assertions.assertEquals(10, resultat.getId());

    }
}
