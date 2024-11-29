package org.example.trainfaster.services.implementations;

import org.example.trainfaster.model.Train;
import org.example.trainfaster.model.Trip;
import org.example.trainfaster.repositories.TrainRepository;
import org.example.trainfaster.repositories.TripRepository;
import org.example.trainfaster.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public void assignTrainToTrip(int trainId, int tripId) {
        Train train = trainRepository.findById(trainId).orElseThrow();
        Trip trip = tripRepository.findById(tripId).orElseThrow();
        List<Trip> trips = train.getTrips();
        trips.add(trip);
        train.setTrips(trips);
        trainRepository.save(train);
    }

    @Override
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @Override
    public void deleteTrainById(Integer id) {
        trainRepository.deleteById(id);
    }

    @Override
    public void updateTrain(Train train) {
        trainRepository.save(train);
    }

    @Override
    public void createTrain(Train train) {
        trainRepository.save(train);
    }
}