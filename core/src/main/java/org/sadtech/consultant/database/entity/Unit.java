package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Unit {

    @Id
    private Long id;
    private String text;
    private String wordKeys;

}
