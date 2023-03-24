package com.api.parkingcontrol.mapper;

import com.api.parkingcontrol.dtos.ResponsibleDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ResponsibleMapper {

    private final ModelMapper mapperResponsible;

    public ParkingSpotModel toResponsibleRequest(ResponsibleDto responsibleRequest) {
        return mapperResponsible.map(responsibleRequest, ParkingSpotModel.class);
    }

    public ResponsibleDto toResponsibleResponse(ParkingSpotModel parkingSpotModel) {
        return mapperResponsible.map(parkingSpotModel, ResponsibleDto.class);
    }

    public Page<ResponsibleDto> toResponsibleList(Page<ParkingSpotModel> resposibleList) {
        return new PageImpl(resposibleList.stream()
                .map(this::toResponsibleResponse)
                .collect(Collectors.toList()));
    }
}