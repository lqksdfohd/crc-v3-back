package com.app.crc.controlleurs;


import com.app.crc.dtos.KlassDto;
import com.app.crc.dtos.ProjetCompletDto;
import com.app.crc.dtos.ProjetDto;
import com.app.crc.entites.Klass;
import com.app.crc.entites.Projet;
import com.app.crc.services.KlassService;
import com.app.crc.services.MapstructService;
import com.app.crc.services.ProjetService;
import com.app.crc.services.ProjetValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
public class ProjetControlleur {

    @Autowired
    private ProjetService projetService;

    @Autowired
    private MapstructService mapstructService;

    @Autowired
    private ProjetValidatorService validatorService;

    @Autowired
    private KlassService klassService;

    @PostMapping(value = "/projet",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjetDto creerProjet(@RequestBody ProjetDto dto){
        Set<ConstraintViolation<ProjetDto>> erreurs = validatorService.validateProjet(dto);
        if( ! erreurs.isEmpty() ){
            String message = "le projet est mal formé";
            throw  new IllegalArgumentException(message);
        }
        Projet input = mapstructService.projetDtoVersProjet(dto);
        Projet resultat = projetService.creerProjet(input);
        return mapstructService.projetVersProjetDto(resultat);
    }


    @GetMapping(value = "/projets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjetDto> listerLesProjetExistants(){
        List<Projet> fromBase = projetService.recupererTousLesProjets();
        List<ProjetDto> output = mapstructService.listeProjetVersListeProjetDto(fromBase);
        return output;
    }

    @GetMapping(value = "/projet/{projetId}")
    public ProjetCompletDto recupererUnProjet(@PathVariable("projetId") Long projetId){
        Projet projet = projetService.recupererUnProjetParId(projetId);
        return mapstructService.projetVersProjetCompletDto(projet);
    }

    @PostMapping(value = "/projet/{projetId}")
    public KlassDto ajouterUneKlassAuProjet(@PathVariable(name = "projetId") Long id, @Valid @RequestBody KlassDto dto){
        Klass klass = mapstructService.klassDtoVersKlass(dto);
        Projet projet = projetService.recupererUnProjetParId(id);
        klass.setProjet(projet);
        Klass result = klassService.sauvegarderUneKlass(klass);
        KlassDto output = mapstructService.klassVersKlassDto(result);
        return output;
    }


    @DeleteMapping("/klass/{klassId}")
    public void supprimerUneKlassDUnProjet(@PathVariable(value = "klassId") Long klassId){
        klassService.supprimerUneKlass(klassId);
    }
}
