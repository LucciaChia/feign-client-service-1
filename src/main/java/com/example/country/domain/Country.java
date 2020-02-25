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
    // pouziva sa, ked mam zoznam napr. Stringov a nie svojich class
    @ElementCollection // nahrada za OneToMany. vytvori sa extra tabulka Country_Border_Country. V Country nie je nic
    private List<String> borderCountries = new ArrayList<>();

    // unidirectional
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "capital_city_UNI_id")
    private Capitalcityuni capitalCityUNI;

    // bidirectional
    @OneToOne(mappedBy = "country",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private CapitalCityBI capitalCityBI;

    // unidirectional parent site
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)// vytvori novu tabulku, kde sa paruju IDs: Country_id a CitizenUNISet_id
    private Set<CitizenUNI> citizenUNISet = new HashSet<>();

    // bidirectional parent site
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CitizenBI> citizenBISet = new HashSet<>();

    // unidirectional     tento ALL mozno nie je dobre
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER) // ked tu nie je fetch EAGER, tak
    private Set<LawUNI> lawUNISet = new HashSet<>();                // @GetMapping("/manytomanyuniremovelawfromacountry") nefunguje

//    // unidirectional     tento ALL mozno nie je dobre
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // ked tu nie je fetch EAGER, tak
//    private Set<LawUNI> lawUNISet = new HashSet<>();                // @GetMapping("/manytomanyuniremovelawfromacountry") nefunguje


    // bidirectional
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<LawBI> lawBISet = new HashSet<>(); // ked nie je fetch EAGER dostavam:
//    org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.example.country.domain.Country.lawBISet, could not initialize proxy - no Session
//    at org.hibernate.collection.internal.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:606) ~[hibernate-core-5.4.6.Final.jar:5.4.6.Final]
//    at org.hibernate.collection.internal.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:218) ~[hibernate-core-5.4.6.Final.jar:5.4.6.Final]
//    at org.hibernate.collection.internal.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:585) ~[hibernate-core-5.4.6.Final.jar:5.4.6.Final]
//    at org.hibernate.collection.internal.AbstractPersistentCollection.read(AbstractPersistentCollection.java:149) ~[hibernate-core-5.4.6.Final.jar:5.4.6.Final]
//    at org.hibernate.collection.internal.PersistentSet.iterator(PersistentSet.java:188) ~[hibernate-core-5.4.6.Final.jar:5.4.6.Final]
//    at java.base/java.util.Collection.removeIf(Collection.java:542) ~[na:na]
}
