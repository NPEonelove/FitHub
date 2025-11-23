package org.npeonelove.backend.dto.train;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CreateTrainResponseDTO {

    private UUID trainId;
    private String title;
    private String description;
    private LocalDateTime createdAt;

}
