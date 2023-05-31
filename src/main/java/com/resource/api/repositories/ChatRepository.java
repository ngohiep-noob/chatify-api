package com.resource.api.repositories;

import com.resource.api.models.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    

}
