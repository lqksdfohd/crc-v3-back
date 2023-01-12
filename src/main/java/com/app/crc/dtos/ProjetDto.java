package com.app.crc.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjetDto {
    @NotBlank
    private String nom;
    private Long id;

}
