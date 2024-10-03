package com.example.blog.constatnts;

public interface RegexConstants {
    String PHONE_NUMBER_BD = "^(01\\d{9}|\\+?8801\\d{9})$";
    String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-@#$%^&*()+])(?=\\S+$).{8,}$";
    String EMAIL = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,5}";
}
