package org.example.trainfaster.repositories;

import org.example.trainfaster.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {

    List<Train> findByTrips_Id(Integer tripId);

}