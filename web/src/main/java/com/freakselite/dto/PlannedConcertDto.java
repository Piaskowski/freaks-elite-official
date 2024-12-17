package com.freakselite.dto;

import com.freakselite.model.PlannedConcert;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PlannedConcertDto {

    // == fields ==
    private int id;
    @NotNull(message = "Data wydarzenia jest obowiązkowa.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private String eventName;
    @NotEmpty(message = "Link do wydarzenia jest obowiązkowy.")
    private String eventUrl;
    private String spot;
    @NotEmpty(message = "Adres wydarzenia jest obowiązkowy.")
    private String street;
    @NotEmpty(message = "Miasto jest obowiązkowe.")
    private String city;


    // == constructors ==
    public PlannedConcertDto(PlannedConcert concert){
        this.id = concert.getId();
        this.date = concert.getDate();
        this.eventName = concert.getEventName();
        this.eventUrl = concert.getEventUrl();
        this.spot = concert.getSpot();
        this.street = concert.getStreet();
        this.city = concert.getCity();
    }

    // == public methods ==
    public String getDateValue(){
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth() + "T" + date.getHour() + ":" + date.getMinute();
    }
    public PlannedConcert toPlannedConcert(){
        PlannedConcert concert = new PlannedConcert();

        concert.setId(id);
        concert.setDate(date);
        concert.setEventName(eventName);
        concert.setEventUrl(eventUrl);
        concert.setSpot(spot);
        concert.setStreet(street);
        concert.setCity(city);

        return concert;
    }

}
