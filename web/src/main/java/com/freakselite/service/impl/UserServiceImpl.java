package com.freakselite.service.impl;

import com.freakselite.dao.UserEntityDao;
import com.freakselite.model.UserEntity;
import com.freakselite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // == fields ==
    private final UserEntityDao userEntityDao;

    // == constructors ==

    @Autowired
    public UserServiceImpl(UserEntityDao userEntityDao) {
        this.userEntityDao = userEntityDao;
    }

    // == public methods ==

    @Override
    public UserEntity findByUsername(String username) {
        return null;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return null;
    }
}
