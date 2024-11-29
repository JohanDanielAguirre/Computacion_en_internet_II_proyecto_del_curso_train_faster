package org.example.trainfaster.controllers;

import org.example.trainfaster.DTO.MaintanceDTO;
import org.example.trainfaster.mapper.MaintanceMapper;
import org.example.trainfaster.model.Maintance;
import org.example.trainfaster.services.interfaces.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    private final MaintanceMapper maintanceMapper = MaintanceMapper.INSTANCE;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MaintanceDTO> createMaintenance(@RequestBody MaintanceDTO maintenanceDTO) {
        Maintance maintenance = maintanceMapper.maintanceDTOToMaintance(maintenanceDTO);
        maintenanceService.createMaintenance(maintenance);
        return ResponseEntity.ok(maintanceMapper.maintanceToMaintanceDTO(maintenance));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MaintanceDTO> updateMaintenance(@PathVariable int id, @RequestBody MaintanceDTO maintenanceDetails) {
        Maintance maintenance = maintenanceService.getAllMaintenances().stream()
                .filter(m -> m.getMaintenance_ID() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        // Update fields from the DTO
        maintenance.setDescription(maintenanceDetails.getDescription());
        maintenance.setMaintenance_Date(maintenanceDetails.getMaintenanceDate());
        maintenance.setStatus(maintenanceDetails.getStatus());

        maintenanceService.updateMaintenance(maintenance);
        return ResponseEntity.ok(maintanceMapper.maintanceToMaintanceDTO(maintenance));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@RequestParam int id) {
        maintenanceService.deleteMaintenanceById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<MaintanceDTO>> getAllMaintenances() {
        List<Maintance> maintenances = maintenanceService.getAllMaintenances();
        List<MaintanceDTO> maintanceDTOs = maintenances.stream()
                .map(maintanceMapper::maintanceToMaintanceDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(maintanceDTOs);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{maintenanceId}/{trainId}")
    public ResponseEntity<Void> assignMaintenanceToTrain(@PathVariable int maintenanceId, @PathVariable int trainId) {
        maintenanceService.assignMaintenanceToTrain(maintenanceId, trainId);
        return ResponseEntity.ok().build();
    }
}
