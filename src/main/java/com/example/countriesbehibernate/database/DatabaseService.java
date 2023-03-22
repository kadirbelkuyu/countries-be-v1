package com.example.countriesbehibernate.database;

import com.example.countriesbehibernate.model.Country;
import com.example.countriesbehibernate.repository.CountryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class DatabaseService {
    final CountryRepository countryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public DatabaseService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> findById(String id) {
        return countryRepository.findById(id);
    }

    public List<Country> findAllByCriteria(String id, String name, String continent, String currency, String phoneCode, String order) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        List<Predicate> predicates = new ArrayList<>();

        // select * from country
        Root<Country> root  = criteriaQuery.from(Country.class);

        // prepare WHERE clause
        if (!id.isEmpty()) {
            // WHERE id ='TR'
            Predicate idPredicate = criteriaBuilder
                    .equal(root.get("id"), id);
            predicates.add(idPredicate);
        }

        if (!name.isEmpty()) {
            Predicate namePredicate = criteriaBuilder
                    .equal(root.get("name"), name);
            predicates.add(namePredicate);
        }


        if (!phoneCode.isEmpty()) {
            try {
                int code = Integer.parseInt(phoneCode);
                Predicate phoneCodePredicate = criteriaBuilder
                        .equal(root.get("phone_code"), code);
                predicates.add(phoneCodePredicate);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (!continent.isEmpty()) {
            Predicate continentPredicate = criteriaBuilder
                    .equal(root.get("continent"), continent);
            predicates.add(continentPredicate);
        }


        if (!currency.isEmpty()) {
            Predicate currencyPredicate = criteriaBuilder
                    .equal(root.get("currency"), currency);
            predicates.add(currencyPredicate);
        }

        // ORDER BY phono_code ASC/DESC

        // select * from country where id like '%TR%' or name like '%Turkey%' and continent like '%AS%'
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery = order != null && order.equals("desc") ?
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("phoneCode")))
                : criteriaQuery.orderBy(criteriaBuilder.asc(root.get("phoneCode")));
        TypedQuery<Country> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }


    public List<Country> insertAllCountries(List<Country> countries) {
        return countryRepository.saveAll(countries);
    }

    public List<Country> updateAllCountries(String id, String name, String continent, String currency, String phoneCode, Country updateCountry) {
        List<Country> oldCountry = findAllByCriteria(id, name, continent, currency, phoneCode, "" );
        List<Country> newCountry = new ArrayList<>();
        for (Country c : oldCountry) {
            if (updateCountry.getId() != null)
                c.setId(updateCountry.getId());
            if (updateCountry.getName() != null)
                c.setName(updateCountry.getName());
            if (updateCountry.getContinent() != null)
                c.setContinent(updateCountry.getContinent());
            if (updateCountry.getCurrency() != null)
                c.setCurrency(updateCountry.getCurrency());
            if (updateCountry.getPhoneCode() > 0)
                c.setPhoneCode(updateCountry.getPhoneCode());
            newCountry.add(c);
        }

        insertAllCountries(newCountry);
        return newCountry;
    }

    public Country updateCountry(String id, Country country) {
        Country updateCountry = countryRepository.findById(id).orElse(null);
        if (updateCountry != null) {
            if (country.getName() != null)
                updateCountry.setName(country.getName());
            if (country.getContinent() != null)
                updateCountry.setContinent(country.getContinent());
            if (country.getCurrency() != null)
                updateCountry.setCurrency(country.getCurrency());
            if (country.getPhoneCode() > 0)
                updateCountry.setPhoneCode(country.getPhoneCode());
        } else
            return null;
        return countryRepository.save(updateCountry);
    }

    public Country findByName(String name) {
        return (Country) countryRepository.findByName(name);
    }


    /*
    // get countries by all params (id, name, continent, currency, phoneCode, order)
    public List<Country> getCountryByFilters(String id, String name, String continent, String currency, String phoneCode, String order) {
        List<Country> countryList = new ArrayList<Country>();
        boolean b = true;
        if (!id.isEmpty()){
            List<Country> list = countryRepository.findById(id).stream().toList();
            countryList.addAll(list);
            b = false;
        }
        if (!name.isEmpty()) {
            List<Country> list = countryRepository.findByName(name);
            b = isB(countryList, b, list);
        }
        if (!continent.isEmpty()) {
            List<Country> list = countryRepository.findByContinent(continent);
            b = isB(countryList, b, list);
        }
        if (!currency.isEmpty()) {
            List<Country> list = countryRepository.findByCurrency(currency);
            b = isB(countryList, b, list);
        }
        if (!phoneCode.isEmpty()) {
            List<Country> list = countryRepository.findByPhoneCode(phoneCode);
            b = isB(countryList, b, list);
        }
        if (!order.isEmpty()) {
            if (b) {
                List<Country> list;
                if (order.equals("asc")) {
                    list = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "phoneCode"));
                } else {
                    list = countryRepository.findAll(Sort.by(Sort.Direction.DESC, "phoneCode"));
                }
                countryList.addAll(list);

            } else {
                List<Country> newList;
                if (order.equals("asc")) {
                    newList = countryList.stream().sorted(Comparator.comparingInt(Country::getPhoneCode)).toList();
                } else {
                    newList = countryList.stream().sorted(Comparator.comparingInt(Country::getPhoneCode).reversed()).toList();
                }
                countryList.clear();
                countryList.addAll(newList);
            }
            b = false;
        }
        if (b == true) {
            countryList = countryRepository.findAll();
        }
        return countryList;

    }

    private boolean isB(List<Country> countryList, boolean b, List<Country> list) {
        if (b) {
            countryList.addAll(list);
        } else {
            List<Country> newList = countryList.stream()
                    .distinct()
                    .filter(list::contains)
                    .collect(Collectors.toList());
            countryList.clear();
            countryList.addAll(newList);
        }
        return false;
    }



    // get all countries
    public List<Country> getAllCountries() {

        return countryRepository.findAll();
    }

    // get country by country id
    public Country getCountryById(String id) {

        return countryRepository.findById(id).orElse(null);
    }

    // get countries by params (continent, currency, phoneCode, order)
    public List<Country> getCountryByParams(String continent, String currency, String phoneCode, String order) {
        List<Country> countryList = new ArrayList<Country>();
        boolean b = true;
        if (!continent.isEmpty()){
            List<Country> list = countryRepository.findByContinent(continent);
            countryList.addAll(list);
            b = false;
        }
        if (!currency.isEmpty()) {
            List<Country> list = countryRepository.findByCurrency(currency);
            b = isB(countryList, b, list);
        }
        if (!phoneCode.isEmpty()) {
            List<Country> list = countryRepository.findByPhoneCode(phoneCode);
            b = isB(countryList, b, list);
        }
        if (!order.isEmpty()) {
            if (b) {
                List<Country> list;
                if (order.equals("asc")) {
                    list = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "phoneCode"));
                } else {
                    list = countryRepository.findAll(Sort.by(Sort.Direction.DESC, "phoneCode"));
                }
                countryList.addAll(list);

            } else {
                List<Country> newList;
                if (order.equals("asc")) {
                    newList = countryList.stream().sorted(Comparator.comparingInt(Country::getPhoneCode)).toList();
                } else {
                    newList = countryList.stream().sorted(Comparator.comparingInt(Country::getPhoneCode).reversed()).toList();
                }
                countryList.clear();
                countryList.addAll(newList);
            }
            b = false;
        }

        return countryList;
    }
*/

}

