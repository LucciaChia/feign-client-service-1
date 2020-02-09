package com.example.country.controllers;

import com.example.country.domain.CitizenUNI;
import com.example.country.domain.Country;
import com.example.country.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/countries")
public class CountryManagerController {

    private final EntityManager entityManager;


    @GetMapping("/manager/setdata")
    @Transactional
    public String testManagerOneToManyUni() {

        Country finland = new Country();
        finland.setName("Finland");

        CitizenUNI jenson = new CitizenUNI();
        jenson.setName("Jenson");
        CitizenUNI anderson = new CitizenUNI();
        anderson.setName("Anderson");
        CitizenUNI fredirkson = new CitizenUNI();
        fredirkson.setName("Fredrikson");

        Set<CitizenUNI> finlanders = new HashSet<>();
        finlanders.add(jenson);
        finlanders.add(anderson);
        finlanders.add(fredirkson);

        finland.setCitizenUNISet(finlanders);

        entityManager.persist(finland);
        entityManager.flush();

        Country japan = new Country();
        japan.setName("Japan");

        CitizenUNI akiko = new CitizenUNI();
        akiko.setName("Akiko");
        CitizenUNI ramen = new CitizenUNI();
        ramen.setName("Ramen");
        CitizenUNI inu = new CitizenUNI();
        inu.setName("Inu");

        Set<CitizenUNI> japaners = new HashSet<>();
        japaners.add(akiko);
        japaners.add(ramen);
        japaners.add(inu);

        japan.setCitizenUNISet(japaners);

        entityManager.persist(japan);
        entityManager.flush();

        finland.getCitizenUNISet().remove(jenson);

        entityManager.remove(japan);

        return "manager was successfull";
    }

//    @GetMapping("/manager/removestudy")
//    @Transactional
//    public String removeStudy() {
//
//
//    }
}
