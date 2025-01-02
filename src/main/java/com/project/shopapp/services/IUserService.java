package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.User;

public interface IUserService {

    User createUser(UserDTO userDTO) throws DataNotFoundException;
    // Login success then return token key --> return String;//
    String login(String phoneNumber, String password);
}
