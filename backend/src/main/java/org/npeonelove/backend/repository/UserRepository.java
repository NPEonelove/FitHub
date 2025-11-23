package org.npeonelove.backend.repository;

import org.npeonelove.backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUserId(UUID userId);

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);
}
