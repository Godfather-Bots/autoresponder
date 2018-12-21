package org.sadtech.consultant.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String token;
    private String lastName;
    private String city;
    private List<SocialNetworks> socialNetworks;


}
