package com.qi_rakibul.sfa.application.dao;

import com.qi_rakibul.sfa.application.domain.UserEntity;

public interface UserDao {

    UserEntity save(UserEntity userEntity);

    boolean existsByEmail(String email);
}
