package org.example.trainfaster.mapper;

import org.example.trainfaster.DTO.TrainDTO;
import org.example.trainfaster.model.Train;
import org.mapstruct.Mapper;

@Mapper
public interface TrainMapper {
    TrainMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TrainMapper.class);

    TrainDTO trainToTrainDTO(Train train);
    Train trainDTOToTrain(TrainDTO trainDTO);
}
