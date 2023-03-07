package com.example.countriesbehibernate.database;

import com.example.countriesbehibernate.model.Country;
import com.example.countriesbehibernate.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    final CountryRepository countryRepository;
    final EntityManager entityManager;


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
            Predicate phoneCodePredicate = criteriaBuilder
                    .equal(root.get("phone_code"), phoneCode);
            predicates.add(phoneCodePredicate);
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
        if (!order.isEmpty()) {
            if (order == "asc") {
                predicates.add((Predicate) criteriaBuilder.asc(root.get("phone_code")));
            }
            else
                predicates.add((Predicate) criteriaBuilder.desc(root.get("phone_code")));
        }

        // select * from country where id like '%TR%' or name like '%Turkey%' and continent like '%AS%'
        Predicate andContinent = criteriaBuilder.and((Predicate) predicates);
        criteriaQuery.where(andContinent);

        TypedQuery<Country> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }

    public List<Country> insertAllCountries(List<Country> countries) {
        return countryRepository.saveAll(countries);
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

