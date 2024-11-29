package org.example.trainfaster.services.interfaces;

import org.example.trainfaster.model.Trip;
import java.util.List;
public interface TripService {

    void assignTripToTrain(int tripId, int trainId);
    void assignTripToTicket(int tripId, int ticketId);

    List<Trip> getAllTrips();

    void deleteTripById(Integer id);

    void updateTrip(Trip trip);

    void  createTrip(Trip trip);

    Trip getTripById(Integer id);
}
