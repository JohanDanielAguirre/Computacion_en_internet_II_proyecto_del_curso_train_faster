package org.example.trainfaster.services.interfaces;

import org.example.trainfaster.model.Maintance;

import java.util.List;

public interface MaintenanceService {
    void assignMaintenanceToTrain(int maintenanceId, int trainId);
    List<Maintance> getAllMaintenances();

    void deleteMaintenanceById(Integer id);

    void updateMaintenance(Maintance maintance);

    void createMaintenance(Maintance maintance);
}