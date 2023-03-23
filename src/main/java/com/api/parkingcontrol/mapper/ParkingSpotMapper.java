package com.api.parkingcontrol.mapper;

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
    private final ModelMapper mapper;

    public ParkingSpotModel toParkingSpotRequest(ParkingSpotDto parkingSpotRequest){
        return mapper.map(parkingSpotRequest, ParkingSpotModel.class);
    }
    public ParkingSpotDto toParkingSpotResponse(ParkingSpotModel parkingSpotModel){
        return mapper.map(parkingSpotModel, ParkingSpotDto.class);
    }
    public Page<ParkingSpotDto> toParkingSpotList(Page<ParkingSpotModel> parkingSpotList){
        return new PageImpl(parkingSpotList.stream()
                .map(this::toParkingSpotResponse)
                .collect(Collectors.toList()));
    }

    public ParkingSpotModel toResponsibleRequest(ResponsibleDto responsibleRequest){
        return mapper.map(responsibleRequest, ParkingSpotModel.class);
    }
    public ResponsibleDto toResponsibleResponse(ParkingSpotModel parkingSpotModel) {
        return mapper.map(parkingSpotModel, ResponsibleDto.class);
    }
    public Page<ResponsibleDto> toResponsibleList(Page<ParkingSpotModel> resposibleList) {
        return new PageImpl(resposibleList.stream()
                .map(this::toResponsibleResponse)
                .collect(Collectors.toList()));
    }
}
