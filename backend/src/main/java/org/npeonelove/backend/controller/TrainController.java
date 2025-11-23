package org.npeonelove.backend.controller;

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

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/trains")
@RequiredArgsConstructor
public class TrainController {

    private final ValidationUtils validationUtils;
    private final TrainService trainService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // создать новую тренировку
    @PostMapping
    public ResponseEntity<CreateTrainResponseDTO> createTrain(@RequestBody @Valid CreateTrainRequestDTO createTrainRequestDTO,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new TrainValidationException(validationUtils.getValidationErrors(bindingResult));
        }

        return ResponseEntity.ok(trainService.createTrain(createTrainRequestDTO));
    }

    // получить тренировку
    @GetMapping("/{trainId}")
    public ResponseEntity<GetTrainResponseDTO> getTrain(@PathVariable("trainId") UUID trainId) {
        return ResponseEntity.ok(trainService.getTrain(trainId));
    }

}
