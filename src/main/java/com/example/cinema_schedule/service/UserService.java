package com.example.cinema_schedule.service;

import com.example.cinema_schedule.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
