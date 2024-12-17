package com.freakselite.dao;

import com.freakselite.model.UserEntity;

public interface UserEntityDao {
    UserEntity findById(int id);
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
