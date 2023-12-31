package com.travelagency.service;

import com.travelagency.dto.Trip;

import java.util.List;
import java.util.Optional;

public interface TripService {

    public List<Trip> getAllTrips();


    public Trip saveTrip(Trip trip);


    public Optional<Trip> getTripById(Long id);


    public Trip updateTrip(Trip trip);


    public void deleteTripById(Long id);

    public void deleteAllTrips();
}
