package com.resource.api.repositories;

import com.resource.api.models.RoomEntity;
import com.resource.api.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByUsersContains(UserEntity user);

}
