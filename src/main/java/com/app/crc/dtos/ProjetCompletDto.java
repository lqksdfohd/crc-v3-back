package com.app.crc.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProjetCompletDto {
    private Long id;
    private String nom;
    private List<KlassDto> listeKlass;

}
