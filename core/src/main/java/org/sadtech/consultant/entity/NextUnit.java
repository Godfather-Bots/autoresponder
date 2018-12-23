package org.sadtech.consultant.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class NextUnit {

    private Long id;
    private Long idNext;

}
