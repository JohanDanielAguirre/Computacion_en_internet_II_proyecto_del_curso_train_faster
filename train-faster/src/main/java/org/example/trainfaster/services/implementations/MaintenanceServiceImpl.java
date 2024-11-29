package org.example.trainfaster.services.implementations;

import org.example.trainfaster.model.Maintance;
import org.example.trainfaster.model.Train;
import org.example.trainfaster.repositories.MaintanceRepository;
import org.example.trainfaster.repositories.TrainRepository;
import org.example.trainfaster.services.interfaces.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintanceRepository maintenanceRepository;
    @Autowired
    private  TrainRepository trainRepository;

    @Override
    public void assignMaintenanceToTrain(int maintenanceId, int trainId) {
        Maintance maintenance = maintenanceRepository.findById(maintenanceId).orElseThrow();
        Train train = trainRepository.findById(trainId).orElseThrow();
        maintenance.setTrain(train);
        maintenanceRepository.save(maintenance);
    }

    @Override
    public List<Maintance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    public void deleteMaintenanceById(Integer id) {
        maintenanceRepository.deleteById(id);
    }

    @Override
    public void updateMaintenance(Maintance maintance) {
        maintenanceRepository.save(maintance);
    }

    @Override
    public void createMaintenance(Maintance maintance) {
        maintenanceRepository.save(maintance);
    }
}
