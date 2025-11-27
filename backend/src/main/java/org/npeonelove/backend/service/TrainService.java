package org.npeonelove.backend.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.backend.dto.train.CreateTrainRequestDTO;
import org.npeonelove.backend.dto.train.CreateTrainResponseDTO;
import org.npeonelove.backend.dto.train.GetTrainResponseDTO;
import org.npeonelove.backend.exception.train.TrainNotFoundException;
import org.npeonelove.backend.mapper.train.TrainMapper;
import org.npeonelove.backend.model.train.Train;
import org.npeonelove.backend.model.user.User;
import org.npeonelove.backend.repository.TrainRepository;
import org.npeonelove.backend.security.SecurityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final UserService userService;
    private final SecurityService securityService;
    private final ModelMapper modelMapper;

    // создать новую тренировку
    @Transactional
    public CreateTrainResponseDTO createTrain(CreateTrainRequestDTO createTrainRequestDTO) {
        Train train = trainRepository.save(trainMapper.toEntity(createTrainRequestDTO));
        return trainMapper.toCreateResponse(train);
    }

    // создать 3 тестовых тренировки
    @Transactional
    public Boolean createDemoTrains(UUID trainId) {
        User user = userService.getUserByUUID(trainId);
        for (int i = 0; i < 3; i++) {
            Train train = Train.builder()
                    .user(user)
                    .title("Train title " + UUID.randomUUID())
                    .description("Train description " + UUID.randomUUID())
                    .build();

            trainRepository.save(train);
        }
        return true;
    }

//    // создать тестовую тренировку
//   @Transactional
//   public Train createDemoTrain(User user) {
//        UUID trainId = UUID.randomUUID();
//
//        Train train = Train.builder()
//                .trainId(trainId)
//                .user(user)
//                .title("Train title " + trainId)
//                .description("Train description " + trainId)
//                .build();
//
//        return trainRepository.save(train);
//   }

    // получить тренировку
    public GetTrainResponseDTO getTrain(UUID trainId) {
        Train train = findTrainById(trainId);
        GetTrainResponseDTO getTrainResponseDTO = trainMapper.toGetResponse(train);
        getTrainResponseDTO.setUserId(train.getUser().getUserId());
        return getTrainResponseDTO;
    }

    // получить все тренировки
    public List<GetTrainResponseDTO> findAll() {
        List<GetTrainResponseDTO> trains = new ArrayList<>();
        for (Train train : trainRepository.findAll()) {
            GetTrainResponseDTO getTrainResponseDTO = trainMapper.toGetResponse(train);
            getTrainResponseDTO.setUserId(train.getUser().getUserId());
            trains.add(getTrainResponseDTO);
        }
        return trains;
    }

    // получить тренировки определенного юзера
    public List<GetTrainResponseDTO> findAllByUser(UUID userId) {
        List<GetTrainResponseDTO> trains = new ArrayList<>();
        for (Train train : trainRepository.findTrainByUser(userService.getUserByUUID(userId))) {
            GetTrainResponseDTO getTrainResponseDTO = trainMapper.toGetResponse(train);
            getTrainResponseDTO.setUserId(train.getUser().getUserId());
            trains.add(getTrainResponseDTO);
        }
        return trains;
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
