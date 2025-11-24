package org.npeonelove.backend.model.user;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.npeonelove.backend.model.train.Train;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@DynamicInsert
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<Train> trains;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;


}
