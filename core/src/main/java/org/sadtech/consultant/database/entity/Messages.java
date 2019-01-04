package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Messages {

    @Id
    @GeneratedValue
    private Long idMess;
    private Long idUser;
    private String text;
    private Long date;
    @Enumerated(EnumType.STRING)
    private SourceMessage source;


}
