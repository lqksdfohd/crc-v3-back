package com.app.crc.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Klass {
    @Id
    @Column(name = "KLASS_ID")
    @GeneratedValue(generator = "klassIdGenerator")
    @TableGenerator(name = "klassIdGenerator", table = "ID_TABLE", pkColumnName = "ID", pkColumnValue = "klass_id"
    ,valueColumnName = "NEXT_ID", schema = "CRC_SCHEMA", initialValue = 100)
    private Long id;
    @Column(name = "NOM")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "PROJET_ID")
    private Projet projet;
}
