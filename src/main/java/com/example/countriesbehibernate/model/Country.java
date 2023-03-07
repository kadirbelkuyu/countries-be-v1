package com.example.countriesbehibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="country")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    private String id;
    private String name;
    private String nativeName;
    private int phoneCode;
    private String continent;
    private String capital;
    private String currency;
    private String languages;
    private String flag;
}