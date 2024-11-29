package org.example.trainfaster.services.implementations;

import org.example.trainfaster.model.Trip;
import org.example.trainfaster.repositories.TicketRepository;
import org.example.trainfaster.repositories.TrainRepository;
import org.example.trainfaster.repositories.TripRepository;
import org.example.trainfaster.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl  implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TicketRepository ticketRepository;



    @Override
    public void assignTripToTrain(int tripId, int trainId) {
        trainRepository.findById(trainId).ifPresent(train -> {
            tripRepository.findById(tripId).ifPresent(trip -> {
                List<Trip> trips = train.getTrips();
                trips.add(trip);
                train.setTrips(trips);
                trainRepository.save(train);
            });
        });
    }

    @Override
    public void assignTripToTicket(int tripId, int ticketId) {
        ticketRepository.findById(ticketId).ifPresent(ticket -> {
            tripRepository.findById(tripId).ifPresent(trip -> {
                ticket.setTrip(trip);
                ticketRepository.save(ticket);
            });
        });
    }

    @Override
    public List<Trip> getAllTrips() {return tripRepository.findAll();}

    @Override
    public void deleteTripById(Integer id) {tripRepository.deleteById(id);}

    @Override
    public void updateTrip(Trip trip) {tripRepository.save(trip);}

    @Override
    public void createTrip(Trip trip) {tripRepository.save(trip);}

    @Override
    public Trip getTripById(Integer id) {return tripRepository.findById(id).orElseThrow();}

}
