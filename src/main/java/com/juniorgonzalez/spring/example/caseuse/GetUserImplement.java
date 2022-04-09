package com.juniorgonzalez.spring.example.caseuse;

import com.juniorgonzalez.spring.example.entity.User;
import com.juniorgonzalez.spring.example.service.UserService;

import java.util.List;

public class GetUserImplement implements  GetUser{

    private UserService userService;

    public GetUserImplement(UserService userService) {
        this.userService = userService;
    }


    @Override
    public List<User> getAll() {
        return userService.getAllUsers();
    }
}
