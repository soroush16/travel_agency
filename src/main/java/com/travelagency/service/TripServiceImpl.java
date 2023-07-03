package com.travelagency.service;

import com.travelagency.exception.NotFoundException;
import com.travelagency.model.Trip;
import com.travelagency.model.Variation;
import com.travelagency.repository.TripRepository;
import com.travelagency.repository.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final VariationRepository variationRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, VariationRepository variationRepository) {
        this.tripRepository = tripRepository;
        this.variationRepository = variationRepository;
    }


    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }


    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }


    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }


    public Trip updateTrip(Trip trip) {

        Optional<Trip> foundTrip = tripRepository.findById(trip.getId());
        if (foundTrip.isPresent()) {
            tripRepository.save(trip);
        }
        Variation variationWithNewFreeSeats;
        if (trip.getAdults() + trip.getChildren() != foundTrip.get().getAdults() + foundTrip.get().getChildren()) {
            int changeInNumberOfPassengers = (trip.getAdults() + trip.getChildren()) - (foundTrip.get().getAdults() + foundTrip.get().getChildren());
            variationWithNewFreeSeats = trip.getPackageVariation();
            if (changeInNumberOfPassengers < 0) {
                variationWithNewFreeSeats.setFreeSeats(variationWithNewFreeSeats.getFreeSeats() + changeInNumberOfPassengers);
            } else {
                variationWithNewFreeSeats.setFreeSeats(variationWithNewFreeSeats.getFreeSeats() - changeInNumberOfPassengers);
            }
            variationRepository.save(variationWithNewFreeSeats);
            return trip;
        }
        throw new NotFoundException("No Trip by ID: " + trip.getId() +" was found");
    }


    public void deleteTripById(Long id) {
        tripRepository.deleteById(id);

    }

    public void deleteAllTrips() {
        tripRepository.deleteAll();

    }

}
