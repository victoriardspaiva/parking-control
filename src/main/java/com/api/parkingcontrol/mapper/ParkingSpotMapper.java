package com.api.parkingcontrol.mapper;

import com.api.parkingcontrol.dtos.ParkingDto;
import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.dtos.ResponsibleDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import com.api.parkingcontrol.configs.ModelMapperConfig;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ParkingSpotMapper {
    private final ModelMapper mapperParking;

    public ParkingSpotModel toParkingSpotRequest(ParkingSpotDto parkingSpotRequest){
        return mapperParking.map(parkingSpotRequest, ParkingSpotModel.class);
    }
    public ParkingSpotDto toParkingSpotResponse(ParkingSpotModel parkingSpotModel){
        return mapperParking.map(parkingSpotModel, ParkingSpotDto.class);
    }
    public ParkingDto toParkingResponse(ParkingSpotModel parkingSpotModel){
        return mapperParking.map(parkingSpotModel, ParkingDto.class);

    }
    public Page<ParkingDto> toParkingList(Page<ParkingSpotModel> parkingList){
        return new PageImpl(parkingList.stream()
                .map(this::toParkingResponse)
                .collect(Collectors.toList()));
    }

}
