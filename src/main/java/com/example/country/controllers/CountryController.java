package com.example.country.controllers;

import com.example.country.domain.*;
import com.example.country.dto.CountryDto;
import com.example.country.facade.CountryFacade;
import com.example.country.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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


    @GetMapping("/country/{id}")
    public CountryDto findCountry(@PathVariable Long id) {
        return countryFacade.findCountryById(id);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ------------------OneToMany UNIDIRECTIONAL relationship----------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

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

    //TODO OPRAVIT NEFUNGUJE !!!
    @GetMapping("/onetomanyuniremovehuman") // NEFUNGUJE - SPYTAT SA
    public String removeOneToManyUniRemoveHuman() {
        Optional<CitizenUNI> citizenUNI1 = citizenUNIRepository.findById(1L);
        CitizenUNI saskiaBaum = citizenUNI1.get();
        System.out.println(saskiaBaum.getName() + " id = " + saskiaBaum.getId());


        Country germany = countryRepository.findById(2L).get();
        System.out.println(germany.getName() + " id = " + germany.getId());
        Set<CitizenUNI> newSet = germany.getCitizenUNISet();
        newSet.remove(citizenUNIRepository.findById(1L));
//        germany.getCitizenUNISet().remove(saskiaBaum);
        germany.setCitizenUNISet(newSet);
        countryRepository.save(germany);
        countryRepository.flush();
        citizenUNIRepository.delete(saskiaBaum);
        citizenUNIRepository.flush();


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
    // -----------------------------------------------------------------------------------------------------------------
    // ------------------OneToMany BIDIRECTIONAL relationship-----------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping("/onetomanysetbidir")
    public String settingBidirectionalDataBI() {

        Country sweden = countryRepository.findById(4L).get();
        Country spain = countryRepository.findById(5L).get();

        CitizenBI manuelaGonzalez = citizenBIRepository.findById(1L).get();
        manuelaGonzalez.setCountry(spain);
        citizenBIRepository.save(manuelaGonzalez);
        CitizenBI rosalinda = citizenBIRepository.findById(2L).get();
        rosalinda.setCountry(spain);
        citizenBIRepository.save(rosalinda);
        CitizenBI esmeralda = citizenBIRepository.findById(3L).get();
        esmeralda.setCountry(spain);
        citizenBIRepository.save(esmeralda);
        CitizenBI nataliaOreiro = citizenBIRepository.findById(4L).get();
        nataliaOreiro.setCountry(spain);
        citizenBIRepository.save(nataliaOreiro);
        CitizenBI henrikSedin = citizenBIRepository.findById(5L).get();
        henrikSedin.setCountry(sweden);
        citizenBIRepository.save(henrikSedin);
        CitizenBI danielAlfredsson = citizenBIRepository.findById(6L).get();
        danielAlfredsson.setCountry(sweden);
        citizenBIRepository.save(danielAlfredsson);
        CitizenBI niklasKronwall = citizenBIRepository.findById(7L).get();
        niklasKronwall.setCountry(sweden);
        citizenBIRepository.save(niklasKronwall);
        CitizenBI henrikLindqvist = citizenBIRepository.findById(8L).get();
        henrikLindqvist.setCountry(sweden);
        citizenBIRepository.save(henrikLindqvist);
        CitizenBI louiEriksson = citizenBIRepository.findById(9L).get();
        louiEriksson.setCountry(sweden);
        citizenBIRepository.save(louiEriksson);

        Set<CitizenBI> latinos = new HashSet<>();
        latinos.add(manuelaGonzalez);
        latinos.add(rosalinda);
        latinos.add(esmeralda);
        latinos.add(nataliaOreiro);

        Set<CitizenBI> swedens = new HashSet<>();
        swedens.add(henrikSedin);
        swedens.add(danielAlfredsson);
        swedens.add(niklasKronwall);
        swedens.add(henrikLindqvist);
        swedens.add(louiEriksson);

        sweden.setCitizenBISet(swedens);
        spain.setCitizenBISet(latinos);

        countryRepository.save(sweden);
        countryRepository.save(spain);

        return "bidirectional data were set";
    }

    @GetMapping("/removeonetomanysetbidir")
    public String addCapitalCityBI() {
        // rosalinda was successfully removed
        CitizenBI rosalinda = citizenBIRepository.findById(2L).get();
        citizenBIRepository.delete(rosalinda);
        citizenBIRepository.flush();

        // sweden was successfully removed
        Country sweden = countryRepository.findById(4L).get();
        countryRepository.delete(sweden);
        countryRepository.flush();

        return "add capital city bi";
    }

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


}