package com.app.crc.entites;

import javax.persistence.*;

@Entity
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
}
