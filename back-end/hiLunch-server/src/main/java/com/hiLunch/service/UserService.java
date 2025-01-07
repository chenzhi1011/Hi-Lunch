package com.hiLunch.service;


import com.hiLunch.dto.UserDTO;
import com.hiLunch.entity.User;

public interface UserService {
    /**
     * userログイン
     * @param userDTO
     * @return
     */
    User login(UserDTO userDTO);

    /**
     * user signup
     * @param userDTO
     * @return
     */
    void signup(UserDTO userDTO);

    /**
     * user update
     * @param userDTO
     * @return
     */
    void updateUserInfo(UserDTO userDTO,String oldPwd);

}
