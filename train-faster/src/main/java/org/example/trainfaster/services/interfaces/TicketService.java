package org.example.trainfaster.services.interfaces;

import org.example.trainfaster.model.Ticket;

import java.util.List;

public interface TicketService {
    void assignTicketToUser(int ticketId, int userId);
    void assignTicketToTrip(int ticketId, int tripId);
    List<Ticket> getAllTickets();

    void deleteTicketById(int id);
    void createTicket(Ticket ticket);
    void updateTicket(Ticket ticket);


    Ticket getTicketById(int id);
}