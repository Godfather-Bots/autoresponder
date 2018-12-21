package org.sadtech.consultant.dao;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class NextStage {

    private long id;
    private long idNext;

}
