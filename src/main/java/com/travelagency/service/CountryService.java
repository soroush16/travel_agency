package com.travelagency.service;

import com.travelagency.dto.Country;
import java.util.List;
import java.util.Optional;

public interface CountryService {

    public List<Country> getAllCountries();


    public Country saveCountry(Country country);


    public Optional<Country> getCountryById(Long id);


    public Country updateCountry(Country country);


    public void deleteCountryById(Long id);

    public void deleteAllCountries();
}
