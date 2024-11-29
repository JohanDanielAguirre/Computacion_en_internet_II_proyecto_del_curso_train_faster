package org.example.trainfaster.DTO;


import lombok.Data;

import java.util.List;

@Data
public class TrainDTO {
    private int id;
    private String model;
    private List<Integer> tripIds;
    private List<Integer> maintenanceIds;
}
