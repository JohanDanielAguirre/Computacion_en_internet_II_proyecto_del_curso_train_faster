package org.example.trainfaster.controllers;

import org.example.trainfaster.DTO.TicketDTO;
import org.example.trainfaster.mapper.TicketMapper;
import org.example.trainfaster.model.Ticket;
import org.example.trainfaster.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = TicketMapper.INSTANCE.ticketDTOToTicket(ticketDTO);
        ticketService.createTicket(ticket);
        return ResponseEntity.ok(TicketMapper.INSTANCE.ticketToTicketDTO(ticket));
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        List<TicketDTO> ticketDTOs = tickets.stream()
                .map(TicketMapper.INSTANCE::ticketToTicketDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ticketDTOs);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(params = {"ticketId", "userId"})
    public ResponseEntity<Void> assignTicketToUser(@PathVariable int ticketId, @PathVariable int userId) {
        ticketService.assignTicketToUser(ticketId, userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(params = {"ticketId", "tripId"})
    public ResponseEntity<Void> assignTicketToTrip(@PathVariable int ticketId, @PathVariable int tripId) {
        ticketService.assignTicketToTrip(ticketId, tripId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(params = {"id"})
    public ResponseEntity<Void> deleteTicket(@RequestParam int id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(params = {"id"})
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable int id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(TicketMapper.INSTANCE.ticketToTicketDTO(ticket));
    }
}