package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    private Long id;
    private String name;
    private String token;
    private String lastName;
    private String city;
//    private List<SocialNetworks> socialNetworks;


}
