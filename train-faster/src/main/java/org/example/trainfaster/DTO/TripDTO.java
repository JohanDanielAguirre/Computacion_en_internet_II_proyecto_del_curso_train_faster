package org.example.trainfaster.DTO;


import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TripDTO {
    private Integer id;
    private String route;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private List<Integer> ticketIds;
    private List<Integer> trainIds;
}
