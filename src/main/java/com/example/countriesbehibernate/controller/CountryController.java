package com.example.countriesbehibernate.controller;

import com.example.countriesbehibernate.database.DatabaseService;
import com.example.countriesbehibernate.database.DbHelper;
import com.example.countriesbehibernate.model.Country;
import lombok.Builder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Builder
public class CountryController {
    final
    DatabaseService databaseService;

    public CountryController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("countries")
    public List<Country> getAllCountries() {
        return databaseService.getAllCountries();
    }

    @GetMapping("countries/{id}")
    public Optional<Country> getCountryById(@PathVariable String id) {
        return databaseService.findById(id);
    }


    @GetMapping("countries/filters")
    public List<Country> getCountryByFilters(@RequestParam(value = "id", defaultValue = "") String id,
                                             @RequestParam(value = "name", defaultValue = "") String name,
                                             @RequestParam(value = "continent", defaultValue = "") String continent,
                                             @RequestParam(value = "currency", defaultValue = "") String currency,
                                             @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode,
                                             @RequestParam(value = "order", defaultValue = "") String order) {
        return databaseService.findAllByCriteria(id, name, continent, currency, phoneCode, order);
    }

    @PutMapping("countries/update")
    public Country updateCountry(@RequestParam(value = "id", defaultValue = "") String id,
                                 @RequestBody Country updateCountry)  {
        return databaseService.updateCountry(id, updateCountry);
    }

    @GetMapping("onetimeinsert")
    public void  insertAllCountries() {
        new DbHelper(databaseService).initializeDatabase();
    }





/*
    @PutMapping("countries/update")
    public List<Country> updateAllCountries(@RequestParam(value = "id", defaultValue = "") String id,
                                       @RequestParam(value = "name", defaultValue = "") String name,
                                       @RequestParam(value = "continent", defaultValue = "") String continent,
                                       @RequestParam(value = "currency", defaultValue = "") String currency,
                                       @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode,
                                       @RequestBody Country updateCountry) {
        return databaseService.updateAllCountry(id, name, continent, currency, phoneCode, updateCountry);
    }

    @GetMapping("onetimeinsert")
    public void  insertAllCountries() {
        new DbHelper(databaseService).initializeDatabase();
    }

    @GetMapping("countries")
    public List<Country>  getAllCountry() {

        return databaseService.getAllCountries();
    }

    @GetMapping("countries/{id}")
    public Country getCountryById(@PathVariable String id) {

        return databaseService.getCountryById(id);
    }

    @GetMapping("countries/params")
    public List<Country> getCountryByParams(@RequestParam(value = "continent", defaultValue = "") String continent,
                                            @RequestParam(value = "currency", defaultValue = "") String currency,
                                            @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode,
                                            @RequestParam(value = "order", defaultValue = "") String order) {
        return databaseService.getCountryByParams(continent, currency, phoneCode, order);
    }

    @GetMapping("countries/filters")
    public List<Country> getCountryByFilters(@RequestParam(value = "id", defaultValue = "") String id,
                                             @RequestParam(value = "name", defaultValue = "") String name,
                                            @RequestParam(value = "continent", defaultValue = "") String continent,
                                            @RequestParam(value = "currency", defaultValue = "") String currency,
                                            @RequestParam(value = "phoneCode", defaultValue = "") String phoneCode,
                                            @RequestParam(value = "order", defaultValue = "") String order) {
        return databaseService.getCountryByFilters(id, name, continent, currency, phoneCode, order);
    }
*/




}


