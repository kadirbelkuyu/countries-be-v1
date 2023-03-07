package com.example.countriesbehibernate.database;

import com.example.countriesbehibernate.model.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.sql.Connection;
import java.util.*;

public class DbHelper {
    DatabaseService databaseService;

    public DbHelper(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    public void initializeDatabase() {
        List<Country> countryList = new ArrayList<>();
        try {
            //parse json to country models and add them to list
            File jsonFile = new File("./asset/countries.json");
            HashMap result = new ObjectMapper().readValue(jsonFile, HashMap.class);
            // get all countries id
            Set keySet = result.keySet();
            // parse json to country models
            Country c = null;
            for (Object id : keySet) {
                // mapping by all countries id
                Map<String, Object> m = (Map<String, Object>) result.get(id.toString());
                String name = m.get("name").toString();
                String nativeName = m.get("native").toString();
                int phone;
                try {
                    phone = Integer.parseInt(m.get("phone").toString());
                } catch (NumberFormatException e) {
                    phone = -999;
                }
                String continent = m.get("continent").toString();
                String capital = m.get("capital").toString();
                String currency = m.get("currency").toString();
                String languages = m.get("languages").toString();
                String flagUrl = getflagUrl(id.toString());

                // create country instance
                c = Country.builder().id(id.toString()).name(name).nativeName(nativeName)
                        .phoneCode(phone).continent(continent).capital(capital).currency(currency)
                        .languages(languages).flag(flagUrl).build();

                // insert the country to database
                countryList.add(c);
            }
            databaseService.insertAllCountries(countryList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String  getflagUrl(String id) {
        return "http://aedemirsen.bilgimeclisi.com/country_flags" + "/" + id + ".svg";
    }

}
