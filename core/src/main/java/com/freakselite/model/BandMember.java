package com.freakselite.model;

import lombok.Data;

@Data
public class BandMember {

    // == fields ==
    private int id;
    private String name;
    private String surname;
    private String nick;
    private String description;
    private String facebookUrl;
    private String instagramUrl;
    private String img;
    private int arrangement;

}
