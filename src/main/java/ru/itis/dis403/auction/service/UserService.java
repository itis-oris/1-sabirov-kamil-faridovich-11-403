package ru.itis.dis403.auction.service;

import ru.itis.dis403.auction.dao.UserDao;
import ru.itis.dis403.auction.model.User;
import ru.itis.dis403.auction.util.ValidateUtil;
import ru.itis.dis403.auction.util.PasswordUtil;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User registerUser(String username, String email, String password, String passwordConfirm) {
        ValidateUtil.validateText(username);
        ValidateUtil.validateEmail(email);
        ValidateUtil.validateEmailExist(email, userDao);
        ValidateUtil.validatePassword(password);

        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setUsername(username.trim());
        user.setEmail(email.toLowerCase().trim());
        user.setPassword(hashedPassword);

        userDao.save(user);
        return user;
    }

    public User authenticate(String email, String password) {
        User user = userDao.findByEmail(email.toLowerCase().trim());
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}