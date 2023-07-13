package com.travelagency.controller;

import com.travelagency.dto.City;
import com.travelagency.repository.CityRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody @Valid City city) {

        return new ResponseEntity<>(cityRepository.save(city), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {

        return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findCityById(@PathVariable long id) {

        Optional<City> foundCity = cityRepository.findById(id);
        if (foundCity.isPresent()) {
            return new ResponseEntity<>(foundCity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable long id, @RequestBody City city) {

        Optional<City> city1 = cityRepository.findById(id);
        if (city1.isPresent()) {
            City foundCountry = city1.get();
            foundCountry.setName(city.getName());
            return new ResponseEntity<>(cityRepository.save(foundCountry), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCityById(@PathVariable Long id) {

        cityRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCities() {

        cityRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
