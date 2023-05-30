package com.resource.api.repositories;

import com.resource.api.models.RoomEntity;
import com.resource.api.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

}
