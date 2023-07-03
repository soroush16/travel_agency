package com.travelagency.controller;


import com.travelagency.model.Country;
import com.travelagency.service.CountryServiceImpl;
import com.travelagency.slugify.TagSlugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private CountryServiceImpl countryService;

    private TagSlugify tagSlugify = new TagSlugify();

    @Autowired
    public CountryController(CountryServiceImpl countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<Country> createCountry (@RequestBody Country country){

        return new ResponseEntity<>(countryService.saveCountry(country), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries (){

            return new ResponseEntity<>(countryService.getAllCountries(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable long id){

            Optional<Country> foundCountry = countryService.getCountryById(id);
            if(foundCountry.isPresent()){
                return new ResponseEntity<>(foundCountry.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping
    public ResponseEntity<Country> updateCountry (@RequestBody Country country){

            return new ResponseEntity<>(countryService.updateCountry(country), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCountryById(@PathVariable Long id){
        countryService.deleteCountryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCountries(){

            countryService.deleteAllCountries();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
