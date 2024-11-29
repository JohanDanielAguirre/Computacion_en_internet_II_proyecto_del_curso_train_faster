package org.example.trainfaster.services.interfaces;

import org.example.trainfaster.model.Train;

import java.util.List;

public interface TrainService {
    void assignTrainToTrip(int trainId, int tripId);
    List<Train> getAllTrains();

    void deleteTrainById(Integer id);

    void updateTrain(Train train);

    void createTrain(Train train);
}
