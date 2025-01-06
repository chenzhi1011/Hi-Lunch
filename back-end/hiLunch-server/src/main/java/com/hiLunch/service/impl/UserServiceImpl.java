package com.hiLunch.service.impl;

import com.hiLunch.constant.MessageConstant;
import com.hiLunch.dto.UserDTO;
import com.hiLunch.entity.User;
import com.hiLunch.exception.AccountNotFoundException;
import com.hiLunch.exception.PasswordErrorException;
import com.hiLunch.mapper.UserMapper;
import com.hiLunch.service.UserService;
import com.hiLunch.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public User login(UserDTO userDTO){
        Long id = userDTO.getId();
        String pwd = userDTO.getPwd();

        User user = userMapper.getById(id);

        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //pwd検証
//        //TODO　user側からのpwdをmd5で暗号化処理
//        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (!pwd.equals(user.getPwd())) {
            //パスワードが合ってない場合
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        return user;
    }
}
