package com.hiLunch.service;


import com.hiLunch.dto.UserDTO;
import com.hiLunch.entity.User;

public interface UserService {
    public User login(UserDTO userDTO);
}
