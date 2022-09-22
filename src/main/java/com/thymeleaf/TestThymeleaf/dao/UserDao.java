package com.thymeleaf.TestThymeleaf.dao;

import com.thymeleaf.TestThymeleaf.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findById(int id);
    User save(User user);
}
