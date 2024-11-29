package org.example.trainfaster.mapper;

import org.example.trainfaster.DTO.TripDTO;
import org.example.trainfaster.model.Trip;
import org.mapstruct.Mapper;

@Mapper
public interface TripMapper {

    TripMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TripMapper.class);
    TripDTO tripToTripDTO(Trip trip);
    Trip tripDTOToTrip(TripDTO tripDTO);

}
