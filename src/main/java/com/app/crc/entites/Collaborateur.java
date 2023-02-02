package com.app.crc.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Collaborateur {

    @Id
    @Column(name = "COLLABORATEUR_ID")
    @GeneratedValue(generator = "collaborateurIdGenerator")
    @TableGenerator(name = "collaborateurIdGenerator", table = "ID_TABLE", pkColumnName = "ID", pkColumnValue = "collaborateur_ids"
            , valueColumnName = "NEXT_ID", schema = "CRC_SCHEMA", initialValue = 100)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRINCIPAL_ID")
    private Klass principal;

    @ManyToOne
    @JoinColumn(name = "COLLABORANT_ID")
    private Klass collaborant;

    public boolean equals(Object obj){
        if(obj instanceof Collaborateur){
            Collaborateur collaborateur = (Collaborateur) obj;
            return getId() != null && getId().equals(collaborateur.getId());
        }
        return false;
    }

    public boolean partagePrincipalAndCollaborant(Collaborateur collaborateur){
        boolean partagePrincipal = getPrincipal().equals(collaborateur.getPrincipal());
        boolean partageCollaborant = getCollaborant().equals(collaborateur.getCollaborant());
        return partagePrincipal && partageCollaborant;
    }
}
