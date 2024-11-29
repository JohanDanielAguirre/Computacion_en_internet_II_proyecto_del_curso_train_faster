package org.example.trainfaster.services.implementations;

import org.example.trainfaster.model.Ticket;
import org.example.trainfaster.model.Trip;
import org.example.trainfaster.model.Users;
import org.example.trainfaster.repositories.TicketRepository;
import org.example.trainfaster.repositories.TripRepository;
import org.example.trainfaster.repositories.UserRepository;
import org.example.trainfaster.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private  TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private  TripRepository tripRepository;

    @Override
    public void assignTicketToUser(int ticketId, int userId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        Users user = userRepository.findById(userId).orElseThrow();
        ticket.setUser(user);
        ticketRepository.save(ticket);
    }

    @Override
    public void assignTicketToTrip(int ticketId, int tripId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        Trip trip = tripRepository.findById(tripId).orElseThrow();
        ticket.setTrip(trip);
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public void deleteTicketById(int id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public void createTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(int id) {
        return ticketRepository.findById(id).orElseThrow();
    }


}