package com.app.crc.controlleurs;


import com.app.crc.dtos.ProjetDto;
import com.app.crc.entites.Projet;
import com.app.crc.services.MapstructService;
import com.app.crc.services.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProjetControlleur {

    @Autowired
    private ProjetService projetService;

    @Autowired
    private MapstructService mapstructService;

    @PostMapping(value = "/projet",consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjetDto creerProjet(@RequestBody @Valid ProjetDto dto){
        Projet input = mapstructService.projetDtoVersProjet(dto);
        Projet resultat = projetService.creerProjet(input);
        return mapstructService.projetVersProjetDto(resultat);
    }
}
