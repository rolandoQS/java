package com.example.java.mappers;

import org.mapstruct.Mapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonMapper {

    //COMMON MAPPING
    default String timestampToString(Timestamp value) {
        return value.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


    default Timestamp stringToTimestamp(String value) {
        List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                // Take a try
                return Timestamp.from(pattern.parse(value).toInstant());

            } catch (ParseException pe) {
                // Loop on
            }
        }
        System.err.println("No known Date format found: " + value);
        return Timestamp.valueOf(value);
    }

    default LocalDateTime stringToLocalDateTime(String value) {
        List<String> knownPatterns = new ArrayList<String>();
        knownPatterns.add("yyyy-MM-dd'T'HH:mm:ss'Z'");
        knownPatterns.add("yyyy-MM-dd");
        knownPatterns.add("yyyy-MM-dd'T'HH:mm:ss");
        knownPatterns.add("yyyy-MM-dd' 'HH:mm:ss");
        knownPatterns.add("yyyy-MM-dd'T'HH:mm:ssXXX");
        knownPatterns.add("dd-MM-yyyy HH:mm:ss");
        for (String pattern : knownPatterns) {
            try {
                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
            } catch (Exception pe) {

            }
        }
        System.err.println("No known Date format found: " + value);
        return LocalDateTime.parse(value);
    }

    default String dateToString(Date value) {
        return value.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    default java.util.Date stringToDate(String value) {
        List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                // Take a try
                return Date.from(pattern.parse(value).toInstant());

            } catch (ParseException pe) {
                // Loop on
            }
        }
        System.err.println("No known Date format found: " + value);
        return Date.valueOf(value);
    }

    default Timestamp localDateTimeToTimestamp(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return Timestamp.valueOf(value);
    }

    default Date localDateToDate(LocalDate value) {
        if (value == null) {
            return null;
        }
        return Date.valueOf(value);
    }

    default LocalDateTime timeStampToLocalDateTime(Timestamp value) {

        if (value == null) {
            return null;
        }
        return value.toLocalDateTime();
    }

    default LocalDate dateToLocalDate(Date value) {

        if (value == null) {
            return null;
        }
        return value.toLocalDate();
    }


}
