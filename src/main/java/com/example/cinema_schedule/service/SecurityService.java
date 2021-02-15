package com.example.cinema_schedule.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
