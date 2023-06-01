package com.resource.api.repositories;

import com.resource.api.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    ArrayList<UserEntity> findByUsernameContainingIgnoreCase(String username);

    List<UserEntity> findAllByUsernameIn(ArrayList<String> usernames);
}
