package com.example.country.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CapitalCityBI")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CapitalCityBI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    // mirroring v pripade OneToOne - ked mame bidirectional vztah, musime mat mirroring v child classe
//    @OneToOne(fetch = FetchType.LAZY)
//    private Country country;
}
