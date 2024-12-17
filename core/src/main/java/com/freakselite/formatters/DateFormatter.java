package com.freakselite.formatters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    private static final DateTimeFormatter dtfD = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter dtfT = DateTimeFormatter.ofPattern("HH:mm");

    public static String parseLocalDateTimeToString(LocalDateTime date){
        return dtfD.format(date) + ", godzina " + dtfT.format(date);
    }

    public static String parseLocalDateTimeToString(LocalDate date) {
        return dtfD.format(date);
    }
}
