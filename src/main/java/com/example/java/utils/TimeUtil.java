package com.example.java.utils;

import com.example.java.exception.InvalidDateFormatException;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class TimeUtil {

    public static Timestamp getTime() {
        ZonedDateTime nowInChile = ZonedDateTime.now(ZoneId.of("Chile/Continental"));
        return Timestamp.valueOf(nowInChile.toLocalDateTime());
    }


    public static Timestamp getTimeByString(String s) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            java.util.Date date = df.parse(s);
            return new Timestamp(date.getTime());
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }


    public static Timestamp getTimeByStringFormat1(String s) {
        List<SimpleDateFormat> formatList = Arrays.asList(new SimpleDateFormat("dd-MM-yyy hh:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        for (SimpleDateFormat df : formatList) {
            try {
                java.util.Date date = df.parse(s);
                return new Timestamp(date.getTime());
            } catch (ParseException pe) {

            }
        }

        throw new InvalidDateFormatException(s, formatList.stream().map(SimpleDateFormat::toPattern).collect(Collectors.toList()));
    }


    public static String dateToUTCTimeStamp(LocalDateTime localDateTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = (Date) inputFormat.parse(localDateTime.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(parsedDate);
    }



    public static Timestamp getNextMonthDateTime(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp.getTime()));
        calendar.add(Calendar.MONTH, 1);
        return new Timestamp(calendar.getTimeInMillis());
    }

}
