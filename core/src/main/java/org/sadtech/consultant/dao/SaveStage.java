package org.sadtech.consultant.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SaveStage {

    @Id
    private long id;
    private long idStage;

}
