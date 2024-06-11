package com.fintechedge.userservice.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Apphelpers {
    public static  String formatDate (LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return date.format(formatter);
    }
}
