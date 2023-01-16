package com.app.crc.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Projet {

    @Id
    @GeneratedValue(generator = "projetIdGenerator", strategy = GenerationType.TABLE)
    @TableGenerator( name = "projetIdGenerator", table = "ID_TABLE", pkColumnName = "ID"
            , pkColumnValue = "projet_ids", valueColumnName = "NEXT_ID", schema = "CRC_SCHEMA"
            ,initialValue = 100)
    @Column(name = "PROJET_ID")
    private Long id;

    @Column(name = "NOM")
    private String nom;

    @OneToMany(mappedBy = "projet")
    private List<Klass> listeKlass;

    public boolean equals(Object obj){
        if(obj instanceof Projet){
            Projet objProjet = (Projet) obj;
            return id != null && id.equals(objProjet.getId());
        }
        return false;
    }

}
