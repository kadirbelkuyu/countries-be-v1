package com.example.countriesbehibernate.repository;

import com.example.countriesbehibernate.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {

    List<Country> findByContinent(String continent);

    List<Country> findByCurrency(String currency);

    List<Country> findByPhoneCode(String phoneCode);

    List<Country> findByName(String name);
}
