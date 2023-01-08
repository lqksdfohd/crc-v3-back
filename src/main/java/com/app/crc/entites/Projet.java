package com.app.crc.entites;

import javax.persistence.*;

@Entity
public class Projet {

    @Id
    @GeneratedValue(generator = "projetIdGenerator", strategy = GenerationType.TABLE)
    @TableGenerator( name = "projetIdGenerator", table = "ID_TABLE", pkColumnName = "ID"
            , pkColumnValue = "projet_ids", valueColumnName = "NEXT_ID")
    @Column(name = "PROJET_ID")
    private Long id;

    @Column(name = "NOM")
    private String nom;

}
