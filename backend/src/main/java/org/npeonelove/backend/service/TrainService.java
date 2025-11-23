package org.npeonelove.backend.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.backend.dto.train.CreateTrainRequestDTO;
import org.npeonelove.backend.dto.train.CreateTrainResponseDTO;
import org.npeonelove.backend.dto.train.GetTrainResponseDTO;
import org.npeonelove.backend.exception.train.TrainNotFoundException;
import org.npeonelove.backend.mapper.train.TrainMapper;
import org.npeonelove.backend.model.train.Train;
import org.npeonelove.backend.repository.TrainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final ModelMapper modelMapper;

    // создать новую тренировку
    @Transactional
    public CreateTrainResponseDTO createTrain(CreateTrainRequestDTO createTrainRequestDTO) {
        Train train = trainRepository.save(trainMapper.toEntity(createTrainRequestDTO));
        return trainMapper.toCreateResponse(train);
    }

    // получить тренировку
    public GetTrainResponseDTO getTrain(UUID trainId) {
        return trainMapper.toGetResponse(findTrainById(trainId));
    }

    // удалить тренировку
    @Transactional
    public void deleteTrain(UUID trainId) {
        trainRepository.delete(findTrainById(trainId));
    }

    // получить сущность Train
    private Train findTrainById(UUID trainId) {
        return trainRepository.findTrainByTrainId(trainId).orElseThrow(
                () -> new TrainNotFoundException("Train with id " + trainId + " not found")
        );
    }

}
