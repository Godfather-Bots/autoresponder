package org.sadtech.consultant.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SocialNetworks {

    @Id
    private Long id;
    private Long idVk;
    private Long idFacebook;

}
