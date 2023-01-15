package com.app.crc.services;

import com.app.crc.dtos.ProjetDto;
import com.app.crc.entites.Projet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapstructService {
    Projet projetDtoVersProjet(ProjetDto dto);

    ProjetDto projetVersProjetDto(Projet projet);

    List<ProjetDto> listeProjetVersListeProjetDto(List<Projet> liste);
}
