package org.npeonelove.backend.model.exercise;

import jakarta.persistence.*;
import lombok.*;
import org.npeonelove.backend.model.train.Train;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Exercise {

    @Id
    @Column(name = "exercise_id")
    private UUID exerciseId;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}