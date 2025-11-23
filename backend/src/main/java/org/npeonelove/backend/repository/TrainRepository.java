package org.npeonelove.backend.repository;

import org.npeonelove.backend.model.train.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainRepository extends JpaRepository<Train, UUID> {

    Optional<Train> findTrainByTrainId(UUID trainId);

}
