package com.freakselite.service;

import com.freakselite.model.UserEntity;

public interface UserService {

    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
