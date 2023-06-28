package com.travelagency.controller;

import com.travelagency.model.Trip;
import com.travelagency.service.TripServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {


    private TripServiceImpl tripServiceImpl;

    @Autowired
    public TripController( TripServiceImpl tripServiceImpl) {
        this.tripServiceImpl = tripServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Trip>> findAllTrips(){

        List<Trip> customerList = tripServiceImpl.getAllTrips();
        return new ResponseEntity<>(customerList, HttpStatus.OK);

        }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable long id) {

        return new ResponseEntity<>(tripServiceImpl.getTripById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Trip> addTrip(@Valid @RequestBody Trip trip) {

        return new ResponseEntity<>(tripServiceImpl.saveTrip(trip), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Trip> updateTrip(@Valid @RequestBody Trip trip) {

        return new ResponseEntity<>(tripServiceImpl.updateTrip(trip), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTripById(@PathVariable long id) {

         tripServiceImpl.deleteTripById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAllTrips() {
        tripServiceImpl.deleteAllTrips();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
