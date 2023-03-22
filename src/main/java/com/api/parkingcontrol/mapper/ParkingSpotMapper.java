package com.api.parkingcontrol.mapper;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;


public class ParkingSpotMapper {
    public static ParkingSpotModel toParkingSpotDto(ParkingSpotDto parkingSpotDto){

        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());

  }

}
