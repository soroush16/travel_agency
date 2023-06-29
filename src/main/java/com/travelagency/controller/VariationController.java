package com.travelagency.controller;


import com.travelagency.model.Variation;
import com.travelagency.repository.VariationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/variations")
public class VariationController {
    private VariationRepository variationRepository;

    @Autowired
    public VariationController(VariationRepository variationRepository) {
        this.variationRepository = variationRepository;
    }

    @PostMapping
    public ResponseEntity<Variation> createVariation(@RequestBody @Valid Variation variation) {
        Variation newVariation = variationRepository.save(new Variation(
                variation.getStartDate()
                , variation.getNumberOfNights()
                , variation.getPlan()
                , variation.getHotel()
                , variation.getPrice()
                , variation.getFreeSeats()));
        return new ResponseEntity<>(newVariation, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Variation>> getAllVariations() {

        List<Variation> allVariations = variationRepository.findAll();
        return new ResponseEntity<>(allVariations, HttpStatus.OK);
    }

    @GetMapping("/{price}")
    public ResponseEntity<List<Variation>> getAllVariationsWithPriceLessThan(@PathVariable double price) {

        List<Variation> allVariationsLessThanPrice = variationRepository.findByPriceLessThan(price);
        return new ResponseEntity<>(allVariationsLessThanPrice, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Variation>> getAllVariationsAfterDate(@PathVariable Date date) {

        List<Variation> allVariationsAfterDate = variationRepository.findByStartDateIsAfter(date);
        return new ResponseEntity<>(allVariationsAfterDate, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Variation> updateVariation(@PathVariable long id, @RequestBody Variation variation) {

        Optional<Variation> foundVariation = variationRepository.findById(id);
        if (foundVariation.isPresent()) {
            Variation newVariation = foundVariation.get();
            newVariation.setStartDate(variation.getStartDate());
            newVariation.setNumberOfNights(variation.getNumberOfNights());
            newVariation.setPrice(variation.getPrice());
            newVariation.setPlan(variation.getPlan());
            newVariation.setFreeSeats(variation.getFreeSeats());
            return new ResponseEntity<>(variationRepository.save(newVariation), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllVariations() {

        variationRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVariationById(@PathVariable long id) {
        variationRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
