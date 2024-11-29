package org.example.trainfaster.repositories;

import org.example.trainfaster.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByTrip_Id(Integer tripId);


}
