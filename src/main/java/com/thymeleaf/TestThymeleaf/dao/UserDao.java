package com.thymeleaf.TestThymeleaf.dao;

import com.thymeleaf.TestThymeleaf.form.UserForm;
import com.thymeleaf.TestThymeleaf.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    // C
    User saveUser(User user);
    // R
    User findById(int id);
    // U
    List<User> editUser(User user, UserForm UserForm);
    // D
    User deleteUser(int id);
}
