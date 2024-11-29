package org.example.trainfaster.repositories;

import org.example.trainfaster.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    Optional<Trip> findById(Integer id);

    Optional<Trip> findByRoute(String route);
    Optional<Trip> findByTickets_Id(Integer ticketId);

    Optional<Trip> findByTrains_Id(Integer trainId);

    Optional<Trip> findByTickets_IdAndTrains_Id(Integer ticketId, Integer trainId);

    Optional<Trip> findByTickets_IdAndTrains_IdAndTickets_Id(Integer ticketId, Integer trainId, Integer ticketId2);
}
