package org.sadtech.consultant.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Message {

    @Id
    private Long idMess;
    private Long idUser;
    private String text;


}
