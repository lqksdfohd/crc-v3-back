package com.app.crc.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Responsabilite {


    @Id
    @Column(name = "RESPONSABILITE_ID")
    @GeneratedValue(generator = "responsabiliteIdGenerator")
    @TableGenerator(name = "responsabiliteIdGenerator", table = "ID_TABLE", pkColumnName = "ID", pkColumnValue = "responsabilite_ids"
    , valueColumnName = "NEXT_ID", schema = "CRC_SCHEMA", initialValue = 100)
    private Long id;

    @Column(name = "TITRE")
    private String titre;

    @ManyToOne
    @JoinColumn(name = "KLASS_ID")
    private Klass klass;

    public boolean hasSameTitre(Responsabilite responsabilite){
        return titre.equals(responsabilite.getTitre());
    }

    public boolean equals(Object obj){
        if(obj instanceof Responsabilite){
            Responsabilite oResponsabilite = (Responsabilite) obj;
            return id != null && id.equals(oResponsabilite.id);
        }else{
            return false;
        }
    }

}
