package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long idMess;
    private Long idUser;
    private String text;
    private String date;


}
