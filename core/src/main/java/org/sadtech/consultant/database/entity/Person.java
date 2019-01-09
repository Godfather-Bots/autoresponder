package org.sadtech.consultant.database.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

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

    @ElementCollection
    @CollectionTable(name="PersonSocialNetworks")
    @MapKeyColumn(name="KeysSocialNetworks", length = 20)
    private Map<String, Integer> socialNetworks = new HashMap<>();

//    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="idMess")
//    private List<Message> messages;

}
