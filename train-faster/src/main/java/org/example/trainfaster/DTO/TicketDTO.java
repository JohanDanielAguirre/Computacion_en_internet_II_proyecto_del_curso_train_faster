package org.example.trainfaster.DTO;


import lombok.Data;

@Data
public class TicketDTO {
    private Integer id;
    private String seat;
    private Integer tripId;
    private Integer userId;
}
