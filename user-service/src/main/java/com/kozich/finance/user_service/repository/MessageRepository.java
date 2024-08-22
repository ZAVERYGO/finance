package com.kozich.finance.user_service.repository;

import com.kozich.finance.user_service.core.enums.MessageStatus;
import com.kozich.finance.user_service.entity.MessageEntity;
import com.kozich.finance.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    List<MessageEntity> findAllByStatus(MessageStatus status);

    Optional<MessageEntity> findByUserUuid(UserEntity userEntity);

}
