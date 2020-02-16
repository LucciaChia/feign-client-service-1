package com.example.country.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LawBI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // v mappedBy je nazov premennej v Country, ktora je ManyToMany: private Set<LawBI> lawBISet = new HashSet<>();
    @ManyToMany(mappedBy = "lawBISet")
    private Set<Country> countrySet = new HashSet<>();
}
