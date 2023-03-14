package com.api.parkingcontrol.services;

confimport com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository parkingSpotRepository;
// comunicação com o repository (Autowired: sugestão do construtores)
// ponto de injeção para ParkingSpotRepository
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

// Quando há um salvamento/deleção em cascata é importante usar a annotation @transactionnal para garentir que o SF realize roolback em caso de erro no processamento
    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }
}

