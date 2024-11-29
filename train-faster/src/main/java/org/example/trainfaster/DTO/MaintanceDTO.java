package org.example.trainfaster.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MaintanceDTO {
    private int maintenanceId;
    private int trainId;
    private Timestamp maintenanceDate;
    private String description;
    private String status;
}
