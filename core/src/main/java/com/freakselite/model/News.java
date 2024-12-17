package com.freakselite.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class News {

    // == fields ==
    private int id;
    private String title;
    private String content;
    private String description;
    private String url;
    private String img;
    private LocalDate date;

}
