package com.example.country.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CapitalCityUNI") // podla tohto sa riadi naplnanie dat v SQL
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Capitalcityuni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // unidirectional association has no annotations or anything else in a child class
}
