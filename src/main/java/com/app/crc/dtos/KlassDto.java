package com.app.crc.dtos;

import lombok.Data;

import java.util.List;

@Data
public class KlassDto {
    private Long id;
    private String nom;
    private List<ResponsabiliteDto> listeResponsabilites;
    private List<CollaborateurDto> listeCollaborateurs;

}
