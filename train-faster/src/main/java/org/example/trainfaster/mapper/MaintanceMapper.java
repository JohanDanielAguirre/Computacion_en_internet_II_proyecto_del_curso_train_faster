package org.example.trainfaster.mapper;

import org.example.trainfaster.DTO.MaintanceDTO;
import org.example.trainfaster.model.Maintance;
import org.mapstruct.Mapper;

@Mapper
public interface MaintanceMapper {
    MaintanceMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(MaintanceMapper.class);

    MaintanceDTO maintanceToMaintanceDTO(Maintance maintance);
    Maintance maintanceDTOToMaintance(MaintanceDTO maintanceDTO);
}
