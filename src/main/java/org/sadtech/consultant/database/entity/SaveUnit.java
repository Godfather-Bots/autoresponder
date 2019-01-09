package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SaveUnit {

    @Id
    private Long id;
    private Long idStage;

    @OneToOne
    @JoinColumn(name = "idPerson", nullable = false)
    private Person user;

}
