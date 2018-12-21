package org.sadtech.consultant.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SocialNetworks {

    @Id
    private long id;
    private long idVk;
    private long idFacebook;
    private long phone;

}
