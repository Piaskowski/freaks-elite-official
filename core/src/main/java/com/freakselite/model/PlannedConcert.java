package com.freakselite.model;

import com.freakselite.formatters.DateFormatter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlannedConcert {

    // == fields ==
    private int id;
    private LocalDateTime date;
    private String eventName;
    private String eventUrl;
    private String spot;
    private String street;
    private String city;

    // == public methods ==
    public LocalDateTime getDate(){
        return date;
    }

    public String getDateParsedToString(){
        return DateFormatter.parseLocalDateTimeToString(date);
    }
}
