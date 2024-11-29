package org.example.trainfaster.controllers;

import org.example.trainfaster.DTO.TripDTO;
import org.example.trainfaster.mapper.TripMapper;
import org.example.trainfaster.model.Trip;
import org.example.trainfaster.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO tripDTO) {
        Trip trip = TripMapper.INSTANCE.tripDTOToTrip(tripDTO);
        tripService.createTrip(trip);
        return ResponseEntity.ok(TripMapper.INSTANCE.tripToTripDTO(trip));
    }

    @PreAuthorize("hasAnyRole()")
    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        List<TripDTO> tripDTOs = trips.stream()
                .map(TripMapper.INSTANCE::tripToTripDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tripDTOs);
    }

    @PreAuthorize("hasAnyRole()")
    @GetMapping(params = "Id")
    public ResponseEntity<TripDTO> getTripById(@PathVariable int id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(TripMapper.INSTANCE.tripToTripDTO(trip));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(params = {"tripId","trainId"})
    public ResponseEntity<Void> assignTripToTrain(@PathVariable int tripId, @PathVariable int trainId) {
        tripService.assignTripToTrain(tripId, trainId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(params = {"tripId","ticketId"})
    public ResponseEntity<Void> assignTripToTicket(@PathVariable int tripId, @PathVariable int ticketId) {
        tripService.assignTripToTicket(tripId, ticketId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(params = {"Id"})
    public ResponseEntity<Void> updateTrip(@PathVariable int id, @RequestBody TripDTO tripDTO) {
        Trip trip = TripMapper.INSTANCE.tripDTOToTrip(tripDTO);
        trip.setId(id);  // Ensure the trip ID is set correctly
        tripService.updateTrip(trip);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(params = {"Id"})
    public ResponseEntity<Void> deleteTrip(@RequestParam int id) {
        tripService.deleteTripById(id);
        return ResponseEntity.noContent().build();
    }
}