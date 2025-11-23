package org.npeonelove.backend.dto.train;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTrainRequestDTO {

    @NotBlank(message = "Введите название тренировки")
    @Size(max = 32, message = "Длина названия тренировки должна быть не более 32 символов")
    private String title;

    @Size(max = 2048, message = "Длина описания тренировки должна быть не более 2048 символов")
    private String description;

}
