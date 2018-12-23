package org.sadtech.consultant.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    private List<SocialNetworks> socialNetworks;


}
