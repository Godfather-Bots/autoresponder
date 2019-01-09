package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String lastName;

    @Column(length = 20)
    private String city;

    private String token;
    private String email;

    @ElementCollection
    @CollectionTable(name = "PersonSocialNetworks")
    @MapKeyColumn(name = "KeysSocialNetworks", length = 20)
    private Map<String, Integer> socialNetworks = new HashMap<>();



}
