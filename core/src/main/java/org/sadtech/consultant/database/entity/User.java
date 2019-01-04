package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.*;
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

    @ElementCollection
    @CollectionTable(name="SocialNetworks", joinColumns=@JoinColumn(name="id"))
    private List<String> socialNetworks;


}
