package com.app.crc.dtos;

import lombok.Data;

@Data
public class CollaborateurDto {
    private Long id;
    private KlassSimpleDto principal;
    private KlassSimpleDto collaborant;
}
