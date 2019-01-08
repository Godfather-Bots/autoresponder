package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long idMess;
    private String text;
    private Long date;
    private String sourceMessage;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private Person user;


}
