package org.npeonelove.backend.dto.train;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class GetTrainResponseDTO {

    private UUID trainId;
    private String title;
    private String description;
    private LocalDateTime createdAt;

}
