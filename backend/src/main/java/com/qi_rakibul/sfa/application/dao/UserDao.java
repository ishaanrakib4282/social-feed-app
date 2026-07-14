package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    UserEntity save(UserEntity userEntity);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID id);
}
