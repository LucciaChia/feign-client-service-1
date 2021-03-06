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

    private final LawUNIRepository lawUNIRepository;
    private final LawBIRepository lawBIRepository;


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

    @GetMapping("/onetomanyuniremovehuman")
    public String removeOneToManyUniRemoveHuman() {
        // najskor najdem cloveka, ktoreho chcem zmazat:
        // (chcem zmazat Saskia Baum, ktora ma ID=1L a je priradena nemecku

        CitizenUNI saskiaBaum = citizenUNIRepository.findById(1L).get();
        System.out.println("Human to be deleted = " + saskiaBaum.getName());

        // najdem krajinu, z ktorej chcem mazat t.j. Nemecko
        Country germany = countryRepository.findById(2L).get();
        System.out.println("Country associated with human = " + germany.getName());

        Set<CitizenUNI> citizenUNISet = germany.getCitizenUNISet();
        Iterator<CitizenUNI> iterator = citizenUNISet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getName());
        }
        // existovali 2 instancie objektu Saskia Baum, bolo treba zmazat vsetky, co remove nevie,
        // na toto sa pouziva removeIf
        //System.out.println("removed = " + citizenUNISet.remove(saskiaBaum));
        System.out.println("removed = " + citizenUNISet.removeIf(entity -> entity.getId() == 1L));

        System.out.println("iterator2:");
        countryRepository.save(germany);

        Iterator<CitizenUNI> iterator2 = citizenUNISet.iterator();
        while (iterator2.hasNext()) {
            System.out.println(iterator2.next().getName());
        }

        System.out.println(" **** ");
        citizenUNIRepository.delete(saskiaBaum);

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

    @GetMapping("/onetomanybiremovecountry")
    public String removeOneToManyBICountry() {

        // sweden was successfully removed
        Country sweden = countryRepository.findById(4L).get();
        countryRepository.delete(sweden);
        countryRepository.flush();

        return "OneToMany BI remove Country was successful";
    }

    @GetMapping("/onetomanybiremovecitizen")
    public String removeOneToManyBICitizen() {
        // rosalinda was successfully removed
        CitizenBI rosalinda = citizenBIRepository.findById(2L).get();
        citizenBIRepository.delete(rosalinda);
        citizenBIRepository.flush();

        return "OneToMany BI remove Citizen was successful";
    }


    // -----------------------------------------------------------------------------------------------------------------
    // ------------------OneToOne UNIDIRECTIONAL relationship-----------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------


    @GetMapping("/onetooneuni")
    public String settingOneToOneUniData() {

        Capitalcityuni bratislava = capitalcityuniRepository.findById(1L).get();
        Capitalcityuni berlin = capitalcityuniRepository.findById(2L).get();
        Capitalcityuni washington = capitalcityuniRepository.findById(3L).get();
        Capitalcityuni stockholm = capitalcityuniRepository.findById(4L).get();
        Capitalcityuni madrid = capitalcityuniRepository.findById(5L).get();

        Country slovakia = countryRepository.findById(1L).get();
        slovakia.setCapitalCityUNI(bratislava);
        countryRepository.save(slovakia);

        Country germany = countryRepository.findById(2L).get();
        germany.setCapitalCityUNI(berlin);
        countryRepository.save(germany);

        Country usa = countryRepository.findById(3L).get();
        usa.setCapitalCityUNI(washington);
        countryRepository.save(usa);
        Country sweden = countryRepository.findById(4L).get();
        sweden.setCapitalCityUNI(stockholm);
        countryRepository.save(sweden);
        Country spain = countryRepository.findById(5L).get();
        spain.setCapitalCityUNI(madrid);
        countryRepository.save(spain);

        return "OneToOne unidirectional data were successfully set";
    }

    @GetMapping("/onetooneuniremovecountry")
    public String removeOneToOneUNICountry() {
        // uspesne zmaze slovensko z country aj bratislavu z capital city
        // country je parent = owner
        Country slovakia = countryRepository.findById(1L).get();
        countryRepository.delete(slovakia);

        return "OneToOne UNI remove a Country was successful";
    }

    @GetMapping("/onetooneuniremovecity")
    public String removeOneToOneUNICity() {

        Country sweden = countryRepository.findById(4L).get();
        sweden.setCapitalCityUNI(null);
        countryRepository.save(sweden); // NEZABUDAT NA SAVE !!! ZASA CHYBAL A POTOM SOM MALA CHYBU:

//        org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException:
//        Referential integrity constraint violation:
//        "FKLGSFRWD56CXJB9OFA46KDN4EK: PUBLIC.COUNTRY FOREIGN KEY(CAPITAL_CITY_UNI_ID) REFERENCES PUBLIC.CAPITAL_CITYUNI(ID) (4)";
//        SQL statement:
//        delete from capital_cityuni where id=? [23503-199]
        Capitalcityuni stockholm = capitalcityuniRepository.findById(4L).get();

        capitalcityuniRepository.delete(stockholm);

        return "OneToOne UNI remove a City was successful";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ------------------OneToOne BIDIRECTIONAL relationship------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping("/onetoonebi")
    public String settingOneToOneBiData() {

        Country slovakia = countryRepository.findById(1L).get();
        Country germany = countryRepository.findById(2L).get();
        Country usa = countryRepository.findById(3L).get();
        Country sweden = countryRepository.findById(4L).get();
        Country spain = countryRepository.findById(5L).get();

        CapitalCityBI bratislava = capitalCityBIRepository.findById(1L).get();
        CapitalCityBI berlin = capitalCityBIRepository.findById(2L).get();
        CapitalCityBI washington = capitalCityBIRepository.findById(3L).get();
        CapitalCityBI stockholm = capitalCityBIRepository.findById(4L).get();
        CapitalCityBI madrid = capitalCityBIRepository.findById(5L).get();

        slovakia.setCapitalCityBI(bratislava);
        countryRepository.save(slovakia);
        germany.setCapitalCityBI(berlin);
        countryRepository.save(germany);
        usa.setCapitalCityBI(washington);
        countryRepository.save(usa);
        sweden.setCapitalCityBI(stockholm);
        countryRepository.save(sweden);
        spain.setCapitalCityBI(madrid);
        countryRepository.save(spain);

        bratislava.setCountry(slovakia);
        capitalCityBIRepository.save(bratislava);
        berlin.setCountry(germany);
        capitalCityBIRepository.save(berlin);
        washington.setCountry(usa);
        capitalCityBIRepository.save(washington);
        stockholm.setCountry(sweden);
        capitalCityBIRepository.save(stockholm);
        madrid.setCountry(spain);
        capitalCityBIRepository.save(madrid);

        return "OneToOne bidirectional data were successfully set";
    }

    @GetMapping("/onetoonebiremovecountry")
    public String removeOneToOneBICountry() {

        Country usa = countryRepository.findById(3L).get();
        countryRepository.delete(usa);

        return "OneToOne BI remove a Country was successful";
    }

    @GetMapping("/onetoonebiremovecity")
    public String removeOneToOneBICity() {

        CapitalCityBI madrid = capitalCityBIRepository.findById(5L).get();
        capitalCityBIRepository.delete(madrid);

        return "OneToOne BI remove a City was successful";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ------------------ManyToMany UNIDIRECTIONAL relationship---------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping("/manytomanyuni")
    public String settingManyToManyUniData() {

        Country slovakia = countryRepository.findById(1L).get();
        Country germany = countryRepository.findById(2L).get();
        Country usa = countryRepository.findById(3L).get();
        Country sweden = countryRepository.findById(4L).get();
        Country spain = countryRepository.findById(5L).get();

        LawUNI notToSteal = lawUNIRepository.findById(1L).get();
        LawUNI notToKill = lawUNIRepository.findById(2L).get();
        LawUNI toPayTaxes = lawUNIRepository.findById(3L).get();

        Set<LawUNI> lawUNISetSlo = new HashSet<>();
        lawUNISetSlo.add(notToSteal);
        lawUNISetSlo.add(notToKill);
        lawUNISetSlo.add(toPayTaxes);
        slovakia.setLawUNISet(lawUNISetSlo);
        countryRepository.save(slovakia);

        Set<LawUNI> lawUNISetGer = new HashSet<>();
        lawUNISetGer.add(notToSteal);
        lawUNISetGer.add(notToKill);
        lawUNISetGer.add(toPayTaxes);
        germany.setLawUNISet(lawUNISetGer);
        countryRepository.save(germany);

        Set<LawUNI> lawUNISetUsa = new HashSet<>();
        lawUNISetUsa.add(notToSteal);
        lawUNISetUsa.add(notToKill);
        lawUNISetUsa.add(toPayTaxes);
        usa.setLawUNISet(lawUNISetUsa);
        countryRepository.save(usa);

        Set<LawUNI> lawUNISetSwe = new HashSet<>();
        lawUNISetSwe.add(notToSteal);
        lawUNISetSwe.add(notToKill);
        lawUNISetSwe.add(toPayTaxes);
        sweden.setLawUNISet(lawUNISetSwe);
        countryRepository.save(sweden);

        Set<LawUNI> lawUNISetSpain = new HashSet<>();
        lawUNISetSpain.add(notToSteal);
        lawUNISetSpain.add(notToKill);
        lawUNISetSpain.add(toPayTaxes);
        spain.setLawUNISet(lawUNISetSpain);
        countryRepository.save(spain);

        return "ManyToMany UNIdirectional data were successfully set";
    }

    @GetMapping("/manytomanyuniremovecountry")
    public String removeManyToManyUniCountry() {

        Country usa = countryRepository.findById(3L).get();
        // tento null je nutny nastavit iba ak mame CascadeType.ALL; Ak mam {CascadeType.PERSIST, CascadeType.MERGE}, tak to netreba,
        // REMOVE funkcia nema zmysel, aby bola kaskadovana
        //usa.setLawUNISet(null); // bez tohoto dostanem:
        //org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException:
        // Referential integrity constraint violation:
        // "FK6WBJ4HD3SUXOAJ4B55G8P0E9G: PUBLIC.COUNTRY_LAWUNISET FOREIGN KEY(LAWUNISET_ID) REFERENCES PUBLIC.LAWUNI(ID) (1)";
        // SQL statement:
        //delete from lawuni where id=? [23503-199]
        countryRepository.save(usa);
        countryRepository.delete(usa);
        countryRepository.flush();

        return "ManyToMany UNI remove a Country was successful";
    }

    @GetMapping("/manytomanyuniremovelawfromacountry")
    public String removeManyToManyUniLaw() {

        LawUNI toPayTaxes = lawUNIRepository.findById(3L).get();

        Country spain = countryRepository.findById(5L).get();
        Set<LawUNI> lawUNISet = spain.getLawUNISet();
        lawUNISet.removeIf(lawUNI -> lawUNI.getName().equals("To Pay Taxes")); // == nefunguje, lebo ide o String, musim pouzit equals()
        countryRepository.save(spain);

        return "ManyToMany UNI remove a City was successful";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ------------------ManyToMany BIDIRECTIONAL relationship----------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping("/manytomanybi")
    public String settingManyToManyBiData() {

        Country slovakia = countryRepository.findById(1L).get();
        Country germany = countryRepository.findById(2L).get();
        Country usa = countryRepository.findById(3L).get();
        Country sweden = countryRepository.findById(4L).get();
        Country spain = countryRepository.findById(5L).get();

        LawBI toMowALawn = lawBIRepository.findById(1L).get();
        LawBI toWaterALawn = lawBIRepository.findById(2L).get();
        LawBI toGrowNewTrees = lawBIRepository.findById(3L).get();
        LawBI notToTameAWildCat = lawBIRepository.findById(4L).get();

        Set<LawBI> lawBISetSlo = new HashSet<>();
        lawBISetSlo.add(toMowALawn);
        lawBISetSlo.add(toWaterALawn);
        lawBISetSlo.add(toGrowNewTrees);
        lawBISetSlo.add(notToTameAWildCat);
        slovakia.setLawBISet(lawBISetSlo);
        countryRepository.save(slovakia);

        Set<LawBI> lawBISetGer = new HashSet<>();
        lawBISetGer.add(toMowALawn);
        lawBISetGer.add(toWaterALawn);
        lawBISetGer.add(toGrowNewTrees);
        lawBISetGer.add(notToTameAWildCat);
        germany.setLawBISet(lawBISetGer);
        countryRepository.save(germany);

        Set<LawBI> lawBISetUsa = new HashSet<>();
        lawBISetUsa.add(toMowALawn);
        lawBISetUsa.add(toWaterALawn);
        lawBISetUsa.add(toGrowNewTrees);
        lawBISetUsa.add(notToTameAWildCat);
        usa.setLawBISet(lawBISetUsa);
        countryRepository.save(usa);

        Set<LawBI> lawBISetSwe = new HashSet<>();
        lawBISetSwe.add(toMowALawn);
        lawBISetSwe.add(toWaterALawn);
        lawBISetSwe.add(toGrowNewTrees);
        lawBISetSwe.add(notToTameAWildCat);
        sweden.setLawBISet(lawBISetSwe);
        countryRepository.save(sweden);

        Set<LawBI> lawBISetSpain = new HashSet<>();
        lawBISetSpain.add(toMowALawn);
        lawBISetSpain.add(toWaterALawn);
        lawBISetSpain.add(toGrowNewTrees);
        lawBISetSpain.add(notToTameAWildCat);
        spain.setLawBISet(lawBISetSpain);
        countryRepository.save(spain);

        return "ManyToMany BIdirectional data were successfully set";
    }

    // TOTO ESTE PREMYSLIET, ale funguje to

    @GetMapping("/manytomanybiremovecountry")
    public String removeManyToManyBiCountry() {

        Country usa = countryRepository.findById(3L).get();

//        usa.setLawBISet(null); // bez tohoto dostanem:
//        //org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException:
//        // Referential integrity constraint violation:
//        // "FK6WBJ4HD3SUXOAJ4B55G8P0E9G: PUBLIC.COUNTRY_LAWUNISET FOREIGN KEY(LAWUNISET_ID) REFERENCES PUBLIC.LAWUNI(ID) (1)";
//        // SQL statement:
//        //delete from lawuni where id=? [23503-199]
//        countryRepository.save(usa);
        countryRepository.delete(usa);
        countryRepository.flush();

        return "ManyToMany BI remove a Country was successful";
    }

    @GetMapping("/manytomanybiremovelawfromacountry")
    public String removeManyToManyBiLawInACountry() {

        LawBI toMowALawn = lawBIRepository.findById(1L).get();

        Country spain = countryRepository.findById(5L).get();
        Set<LawBI> lawBISet = spain.getLawBISet();
        lawBISet.removeIf(lawBI -> lawBI.getName().equals("To Mow A Lawn")); // == nefunguje, lebo ide o String, musim pouzit equals()
        countryRepository.save(spain);

        return "ManyToMany BI remove a Law from a Country was successful";
    }

    @GetMapping("/manytomanybiremovelaw")
    public String removeManyToManyBiLaw() {

        LawBI notToTameAWildCat = lawBIRepository.findById(4L).get();

        for (long i = 1; i <= countryRepository.findAll().size(); i++) {
            Country country = countryRepository.findById(i).get();

            if (country != null) {
                Set<LawBI> lawBISet = country.getLawBISet();
                lawBISet.removeIf(lawBI -> lawBI.getName().equals("Not To Tame A Wild Cat"));
                countryRepository.save(country);
            }
        }

        lawBIRepository.delete(notToTameAWildCat);


        return "ManyToMany BI remove a Law was successful";
    }

}