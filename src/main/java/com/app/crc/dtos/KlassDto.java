package com.app.crc.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class KlassDto {
    private Long id;
    @NotBlank
    private String nom;
    private List<ResponsabiliteDto> listeResponsabilites;
    private List<CollaborateurDto> listeCollaborateurs;
    private boolean creation;

}
