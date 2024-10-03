package com.example.blog.helper;


import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    public static Boolean checkValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+1\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static Boolean checkValidEmail(String emailAddress) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    public static boolean isValidLanguageEnglish(String text) {
        if (ObjectUtils.isEmpty(text)) {
            return false;
        }
        return Pattern.matches("^[A-Za-z]+$", text);
    }



    public static Boolean isValidBirthCertificate(String birthCertificateNumber) {
        if (ObjectUtils.isEmpty(birthCertificateNumber)) {
            return false;
        }
        String nidRegex = "^(\\d{17})$";
        Pattern pattern = Pattern.compile(nidRegex);
        Matcher matcher = pattern.matcher(birthCertificateNumber);
        return matcher.matches();
    }

    public static Boolean isValidPassport(String passportNumber) {
        if (passportNumber.matches("[a-zA-Z0-9]+")) {
            return passportNumber.length() == 10;
        }
        return false;
    }
    public static Boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?/~\\\\-])(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,15}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String getFieldError(BindingResult bindingResult) {
        StringBuilder fieldErrors = new StringBuilder();
        int errorCount = bindingResult.getAllErrors().size();
        if (errorCount > 0) {
            for (int i = 0; i < errorCount; i++) {
                ObjectError error = bindingResult.getAllErrors().get(i);
                if (i > 0) {
                    fieldErrors.append("; ");
                }
                fieldErrors.append(error.getDefaultMessage());
                if (i == 4 && errorCount > 5) {
                    fieldErrors.append("; and there are ").append(errorCount - 5).append(" more errors.");
                    break;
                }
            }
            if (errorCount == 1) {
                fieldErrors.append(".");
            }
        } else {
            fieldErrors.append("No errors.");
        }
        return fieldErrors.toString();
    }


}
