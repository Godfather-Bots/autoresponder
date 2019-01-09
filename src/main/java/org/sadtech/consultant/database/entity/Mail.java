package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Mail {

    @Id
    @GeneratedValue
    private Long idMess;
    private String text;
    private Long date;
    private String sourceMessage;

    @ManyToOne
    @JoinColumn(name = "idPerson", nullable = false)
    private Person person;


}
