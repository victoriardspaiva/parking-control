package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingDto;
import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.dtos.ResponsibleDto;
import com.api.parkingcontrol.mapper.ResponsibleMapper;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.parkingcontrol.mapper.ParkingSpotMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    private final ParkingSpotMapper mapperParking;
    private final ResponsibleMapper mapperResponsible;
    final ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Sport already registered for this apartment/block!");

        parkingSpotDto.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        ParkingSpotModel parkingSpotModel = mapperParking.toParkingSpotRequest(parkingSpotDto);
        ParkingSpotModel savedParkingSpot = parkingSpotService.save(parkingSpotModel);
        ParkingSpotDto parkingSpotDtoResponse = mapperParking.toParkingSpotResponse(savedParkingSpot);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotDtoResponse);
    }

    @GetMapping
    public ResponseEntity<Page<ParkingDto>> getAllParkingSpots(@PageableDefault(                                                               page = 0,
            size = 10,sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        Page<ParkingSpotModel> parkingSpotModel = parkingSpotService.findAll(pageable);
        Page<ParkingDto> ParkingDto = mapperParking.toParkingList(parkingSpotModel);
        return ResponseEntity.status(HttpStatus.OK).body(ParkingDto);
    }

    @GetMapping("/responsible")
    public ResponseEntity<Page<ResponsibleDto>> getAllResponsible(@PageableDefault(
            page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC)Pageable pageable){
        Page<ParkingSpotModel> parkingSpotsModel = parkingSpotService.findAll(pageable);
        Page<ResponsibleDto> responsibleDto = mapperResponsible.toResponsibleList(parkingSpotsModel);
        return ResponseEntity.status(HttpStatus.OK).body(responsibleDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotDto> getOneParkingSpot(@PathVariable(value = "id")UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ParkingSpotDto parkingSpotResponse = mapperParking.toParkingSpotResponse(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parkig Spot not found.");
        parkingSpotService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpotUm(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        parkingSpotDto.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        ParkingSpotModel parkingSpotModel = mapperParking.toParkingSpotRequest(parkingSpotDto);
        ParkingSpotModel savedParkingSpot = parkingSpotService.save(parkingSpotModel);
        ParkingSpotDto parkingSpotResponse = mapperParking.toParkingSpotResponse(savedParkingSpot);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotResponse);
    }


}
