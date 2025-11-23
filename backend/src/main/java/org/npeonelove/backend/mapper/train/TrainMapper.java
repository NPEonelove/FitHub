package org.npeonelove.backend.mapper.train;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.npeonelove.backend.dto.train.CreateTrainRequestDTO;
import org.npeonelove.backend.dto.train.CreateTrainResponseDTO;
import org.npeonelove.backend.dto.train.GetTrainResponseDTO;
import org.npeonelove.backend.model.train.Train;

@Mapper(componentModel = "spring")
public interface TrainMapper {

    Train toEntity(CreateTrainRequestDTO createTrainRequestDTO);

    CreateTrainResponseDTO toCreateResponse(Train train);
    GetTrainResponseDTO toGetResponse(Train train);

}
