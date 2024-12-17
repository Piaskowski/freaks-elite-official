package com.freakselite.model;

import lombok.Data;

import java.util.List;

@Data
public class UserEntity {
    int id;
    String username;
    String email;
    String password;
    List<Role> roles;
}
