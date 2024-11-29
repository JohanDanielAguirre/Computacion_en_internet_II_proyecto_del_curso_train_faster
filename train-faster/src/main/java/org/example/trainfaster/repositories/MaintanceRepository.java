package org.example.trainfaster.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.trainfaster.model.Maintance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintanceRepository extends JpaRepository<Maintance,Integer> {

    List<Maintance> findAllByTrain_Id(Integer trainId);

    List<Maintance> findAllByTrain_IdAndTrain_Id(Integer trainId, Integer trainId2);

    List<Maintance> findAllByTrain_IdAndTrain_IdAndTrain_Id(Integer trainId, Integer trainId2, Integer trainId3);

    List<Maintance> findAllByTrain_IdAndTrain_IdAndTrain_IdAndTrain_Id(Integer trainId, Integer trainId2, Integer trainId3, Integer trainId4);
}
