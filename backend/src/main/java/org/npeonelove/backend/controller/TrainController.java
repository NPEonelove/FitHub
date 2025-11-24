package org.npeonelove.backend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.backend.dto.train.CreateTrainRequestDTO;
import org.npeonelove.backend.dto.train.CreateTrainResponseDTO;
import org.npeonelove.backend.dto.train.GetTrainResponseDTO;
import org.npeonelove.backend.exception.train.TrainValidationException;
import org.npeonelove.backend.service.TrainService;
import org.npeonelove.backend.utils.ValidationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/trains")
@RequiredArgsConstructor
//@Hidden
public class TrainController {

    private final ValidationUtils validationUtils;
    private final TrainService trainService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // создать новую тренировку
    @Hidden
    @PostMapping
    public ResponseEntity<CreateTrainResponseDTO> createTrain(@RequestBody @Valid CreateTrainRequestDTO createTrainRequestDTO,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new TrainValidationException(validationUtils.getValidationErrors(bindingResult));
        }

        return ResponseEntity.ok(trainService.createTrain(createTrainRequestDTO));
    }

    // создать демо тренировки для юзера
    @PostMapping("/{userId}/create-demo-trains")
    public ResponseEntity<Boolean> createDemoTrains(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(trainService.createDemoTrains(userId));
    }

    // получить тренировки определенного пользователя
    @GetMapping("/{userId}/get-all-trains")
    public ResponseEntity<List<GetTrainResponseDTO>> getTrainsByUserId(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(trainService.findAllByUser(userId));
    }

    // получить все тренировки
    @GetMapping()
    public ResponseEntity<List<GetTrainResponseDTO>> getTrains() {
        return ResponseEntity.ok(trainService.findAll());
    }

    // получить тренировку
    @GetMapping("/{trainId}")
    public ResponseEntity<GetTrainResponseDTO> getTrain(@PathVariable("trainId") UUID trainId) {
        return ResponseEntity.ok(trainService.getTrain(trainId));
    }

}
