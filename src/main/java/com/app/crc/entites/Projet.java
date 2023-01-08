package com.app.crc.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Projet {

    @Id
    @GeneratedValue(generator = "projetIdGenerator", strategy = GenerationType.TABLE)
    @TableGenerator( name = "projetIdGenerator", table = "ID_TABLE", pkColumnName = "ID"
            , pkColumnValue = "projet_ids", valueColumnName = "NEXT_ID", schema = "CRC_SCHEMA")
    @Column(name = "PROJET_ID")
    private Long id;

    @Column(name = "NOM")
    private String nom;

}
