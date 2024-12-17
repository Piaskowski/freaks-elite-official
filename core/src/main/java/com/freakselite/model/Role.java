package com.freakselite.model;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    int id;
    String name;
    List<UserEntity> users;
}
