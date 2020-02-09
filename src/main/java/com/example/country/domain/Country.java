package com.example.country.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Country")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    // unidirectional
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "capital_city_UNI_id")
//    private Capitalcityuni capitalCityUNI;
//
//    // bidirectional
//    @OneToOne(mappedBy = "country",
//            cascade = CascadeType.ALL)
//    @JoinColumn(name = "capital_city_BI_id")
//    private CapitalCityBI capitalCityBI;

    // unidirectional
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)// vytvori novu tabulku, kde sa paruju IDs: Country_id a CitizenUNISet_id
    private List<CitizenUNI> citizenUNISet = new ArrayList<>();

    // bidirectional
}
