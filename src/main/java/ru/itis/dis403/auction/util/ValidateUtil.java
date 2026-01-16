package ru.itis.dis403.auction.util;

import ru.itis.dis403.auction.dao.UserDao;

import java.time.LocalDate;

public class ValidateUtil {
    public static void validateEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9@._-]+$") || email.toLowerCase().contains("xn--")) {
            throw new SecurityException("Email может содержать только английские буквы, цифры и символы @ . _ -");
        }
    }

    public static void validateEmailExist(String email, UserDao userDao) {
        if (userDao.findByEmail(email) != null) {
            throw new SecurityException("Пользователь с такой почтой уже существует");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new SecurityException("Пароль должен содержать минимум 6 символов");
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        if (!hasLetter || !hasDigit) {
            throw new SecurityException("Пароль должен содержать хотя бы одну букву и одну цифру");
        }
    }

    public static void validateText(String text) {
        if (text == null) return;
        String[] dangerousPatterns = {
                "<script", "</script", "javascript:", "onload=", "onerror=",
                "onclick=", "eval(", "alert(", "document.cookie", "window.location",
                "<iframe", "<object", "<embed", "<form", "onmouse"
        };
        String lowerText = text.toLowerCase();
        for (String pattern : dangerousPatterns) {
            if (lowerText.contains(pattern)) {
                throw new SecurityException("Текст содержит запрещенные конструкции: " + pattern);
            }
        }
    }
}
