package com.example.countriesbehibernate.controller;

import com.example.countriesbehibernate.database.DatabaseService;
import com.example.countriesbehibernate.model.Country;
import lombok.Builder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Builder
public class CountryController {
    final
    DatabaseService databaseService;

    public CountryController(DatabaseService databaseService) {
        this.databaseService = databaseService;
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




/*
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

