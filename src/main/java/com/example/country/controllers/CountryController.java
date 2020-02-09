package com.example.country.controllers;

import com.example.country.domain.CapitalCityBI;
import com.example.country.domain.Capitalcityuni;
import com.example.country.domain.CitizenUNI;
import com.example.country.domain.Country;
import com.example.country.dto.CountryDto;
import com.example.country.facade.CountryFacade;
import com.example.country.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.expression.SimpleCaseExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;
    private final CountryFacade countryFacade;

    private final CapitalcityuniRepository capitalcityuniRepository;
    private final CapitalCityBIRepository capitalCityBIRepository;

    private final CitizenUNIRepository citizenUNIRepository;
    private final CitizenBIRepository citizenBIRepository;

    @GetMapping("/")
    public String test() {
        return "test";
    }


    @GetMapping("/country/{id}")
    public CountryDto findCountry(@PathVariable Long id) {
        return countryFacade.findCountryById(id);
    }

    @GetMapping("/onetomanyuni")
    public String addOneToManyUni() {
        Optional<CitizenUNI> citizenUNI1 = citizenUNIRepository.findById(1L);
        CitizenUNI saskiaBaum = citizenUNI1.get();
        citizenUNIRepository.save(saskiaBaum);
        Optional<CitizenUNI> citizenUNI2 = citizenUNIRepository.findById(2L);
        CitizenUNI victorKlein = citizenUNI2.get();
        citizenUNIRepository.save(victorKlein);
        Optional<CitizenUNI> citizenUNI3 = citizenUNIRepository.findById(3L);
        CitizenUNI heinrichHolz = citizenUNI3.get();
        citizenUNIRepository.save(heinrichHolz);
        Optional<CitizenUNI> citizenUNI4 = citizenUNIRepository.findById(4L);
        CitizenUNI johnSmith = citizenUNI4.get();
        citizenUNIRepository.save(johnSmith);
        Optional<CitizenUNI> citizenUNI5 = citizenUNIRepository.findById(5L);
        CitizenUNI emmaStone = citizenUNI5.get();
        citizenUNIRepository.save(emmaStone);
        Optional<CitizenUNI> citizenUNI6 = citizenUNIRepository.findById(6L);
        CitizenUNI jenniferAniston = citizenUNI6.get();
        citizenUNIRepository.save(jenniferAniston);
        Optional<CitizenUNI> citizenUNI7 = citizenUNIRepository.findById(7L);
        CitizenUNI fernandoHernandez = citizenUNI7.get();
        citizenUNIRepository.save(fernandoHernandez);
        Country germany = countryRepository.findById(2L).get();
        Set<CitizenUNI> germanSet = new HashSet<>();
        germanSet.add(saskiaBaum);
        germanSet.add(victorKlein);
        germanSet.add(heinrichHolz);
        germany.setCitizenUNISet(germanSet);
        countryRepository.save(germany);
        Country usa = countryRepository.findById(3L).get();
        Set<CitizenUNI> usaSet = new HashSet<>();
        usaSet.add(johnSmith);
        usaSet.add(emmaStone);
        usaSet.add(jenniferAniston);
        usa.setCitizenUNISet(usaSet);
        countryRepository.save(usa);
        Country spain = countryRepository.findById(5L).get();
        Set<CitizenUNI> spanishSet = new HashSet<>();
        spanishSet.add(fernandoHernandez);
        spain.setCitizenUNISet(spanishSet);
        countryRepository.save(spain);

        return "One to Many unidirectional - data are set";
    }

    // taketo vymazanie cloveka si nedomysli, mozno v bidirectional to pojde - uvidime
    // tu dostavam chybu:
//    org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException:
//    Referential integrity constraint violation:
//    "FKBH3BJLKTNR8CA2BED57NW64H: PUBLIC.COUNTRY_CITIZENUNISET FOREIGN KEY(CITIZENUNISET_ID) REFERENCES PUBLIC.CITIZENUNI(ID) (1)"; SQL statement:
//    delete from citizenuni where id=? [23503-199]

//    @GetMapping("/onetomanyuniremovehuman")
//    public String removeOneToManyUniRemoveHumanFailed() {
//        Optional<CitizenUNI> citizenUNI1 = citizenUNIRepository.findById(1L);
//        CitizenUNI saskiaBaum = citizenUNI1.get();
//        citizenUNIRepository.delete(saskiaBaum);
//        citizenBIRepository.flush();
//        return "One to Many unidirectional - child data - a person called Saskia Baum id = 1 was removed";
//    }

    @GetMapping("/onetomanyuniremovehuman")
    public String removeOneToManyUniRemoveHuman() {
        Optional<CitizenUNI> citizenUNI1 = citizenUNIRepository.findById(1L);
        CitizenUNI saskiaBaum = citizenUNI1.get();
        System.out.println(saskiaBaum.getName() + " id = " + saskiaBaum.getId());


        Country germany = countryRepository.findById(2L).get();
        System.out.println(germany.getName() + " id = " + germany.getId());
        germany.getCitizenUNISet().remove(citizenUNIRepository.findById(1L));
        //germany.getCitizenUNISet().remove(saskiaBaum);
        citizenUNIRepository.delete(saskiaBaum);
        citizenUNIRepository.flush();
        countryRepository.save(germany);
        countryRepository.flush();


        System.out.println("Size is = " + countryRepository.findById(2L).get().getCitizenUNISet().size());
        Iterator<CitizenUNI> iterator = countryRepository.findById(2L).get().getCitizenUNISet().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getName());
        }
        System.out.println("***");

//        countryRepository.flush();
//        citizenUNIRepository.deleteById(1L);
//        citizenBIRepository.flush();
        return "One to Many unidirectional - child data - a person called Saskia Baum id = 1 was removed";
    }

    @GetMapping("/onetomanyuniremovecountry")
    public String removeOneToManyUniRemoveCountryAndWatchCascadingInAction() {
        Country usa = countryRepository.findById(3L).get();
        countryRepository.delete(usa);
        countryRepository.flush();
        return "One to Many unidirectional - parent data - a country usa id = 3 was removed";
    }
//
//    @GetMapping("/addcapitalcityuni")
//    public String addCapitalCityUNI() {
//        // ulozim do UNI tabulky a savenem
//        Capitalcityuni capitalcityuni = new Capitalcityuni();
//        capitalcityuni.setName("Berlin-UNI");
//        capitalcityuniRepository.save(capitalcityuni);
//        // naviazem na spravnu krajinu a ulozim do country tabulky a savenem
//        Optional<Country> country = countryRepository.findById(2L);
//        country.get().setCapitalCityUNI(capitalcityuni);
//        countryRepository.save(country.get());
//        return "add capital city uni";
//    }
//
//    @GetMapping("/addcapitalcitybi")
//    public String addCapitalCityBI() {
//        CapitalCityBI capitalCityBI = new CapitalCityBI();
//        capitalCityBI.setName("Washington-BI");
//        capitalCityBIRepository.save(capitalCityBI);
//        Optional<Country> country = countryRepository.findById(3L);
//        country.get().setCapitalCityBI(capitalCityBI);
//        countryRepository.save(country.get());
//        capitalCityBI.setCountry(country.get());
//        capitalCityBIRepository.save(capitalCityBI);
//        return "add capital city bi";
//    }

//    @GetMapping("/country/{id}")
//    public CountryDto removeCapitalCityUNI(@PathVariable Long id) {
//        return countryFacade.findCountryById(id);
//    }
//
//    @GetMapping("/country/{id}")
//    public CountryDto removeCapitalCityBI(@PathVariable Long id) {
//        return countryFacade.findCountryById(id);
//    }
//
//    @GetMapping("/country/{id}")
//    public CountryDto removeCapitalCityBI(@PathVariable Long id) {
//        return countryFacade.findCountryById(id);
//    }



//    @GetMapping("/")
//    public List<Country> findCountries() {
//        return countryRepository.findAll();
//    }

}