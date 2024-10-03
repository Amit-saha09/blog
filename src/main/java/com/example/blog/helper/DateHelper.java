package com.example.blog.helper;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateHelper {
    // Method to convert a string to a Date
    public static Date convertStringToDate(String dateString) throws ParseException {
        String inputFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        return sdf.parse(dateString);
    }

    public static Date convertStringToDateForMapper(String dateString) throws ParseException {
        String inputFormat = "yyyy-MM-dd";
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(inputFormat));

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String convertDateToString(Date date) {
        String outputFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(outputFormat);
        return sdf.format(date);
    }

    private static String convertDateToLocalDateAndFormat(Date date) {
        String inputFormat = "yyyy-MM-dd";
        LocalDateTime localDateTime = date
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.format(DateTimeFormatter.ofPattern(inputFormat));
    }

    private static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String ConvertDateToFormattedString(Date date, String format) {
        if (ObjectUtils.isEmpty(date) && ObjectUtils.isEmpty(format)) {
            return "invalid input";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String ConvertLocalDateToFormattedString(LocalDate localDate, String format) {
        if (ObjectUtils.isEmpty(localDate) && ObjectUtils.isEmpty(format)) {
            return "invalid input";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    public static boolean jobExpiredDateLeft(Date inputDate) {
        Date currentDate = new Date();
        return inputDate.before(currentDate);
    }

    public static boolean jobExpiredLocalDateLeft(LocalDate inputLocalDate) {
        LocalDate currentDate = LocalDate.now();
        return inputLocalDate.isBefore(currentDate);
    }

    public static boolean isDobBeforeTodayDate(Date date) {
        Date currentDate = new Date();
        return date.before(currentDate);
    }

    public static boolean isDobBeforeTodayLocalDate(LocalDate localDate) {
        LocalDate currentDate = LocalDate.now();
        return localDate.isBefore(currentDate);
    }

    public static String convertDateToLocalDateTimeAndFormat(Date date) {
        if(ObjectUtils.isEmpty(date)){
            return null;
        }
        String inputFormat = "yyyy-MM-dd HH:mm:ss";

        LocalDateTime localDateTime = date
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.format(DateTimeFormatter.ofPattern(inputFormat));
    }

    public static String extractYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }

}
