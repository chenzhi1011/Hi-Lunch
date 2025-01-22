package com.hiLunch.service.impl;

import com.hiLunch.constant.MessageConstant;
import com.hiLunch.context.BaseContext;
import com.hiLunch.dto.UserDTO;
import com.hiLunch.entity.User;
import com.hiLunch.exception.AccountExistException;
import com.hiLunch.exception.AccountNotFoundException;
import com.hiLunch.exception.PasswordErrorException;
import com.hiLunch.mapper.UserMapper;
import com.hiLunch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
@Slf4j
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
       //user側からのpwdをmd5で暗号化処理
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (!pwd.equals(user.getPwd())) {
            //パスワードが合ってない場合
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        return user;
    }

    public void signup(UserDTO userDTO){
        //登録するアカウントが存在するかどうかを確認する
        User userExistCheck = userMapper.getById(userDTO.getId());
        if(userExistCheck != null){
            throw new AccountExistException(MessageConstant.ACCOUNT_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        String password = DigestUtils.md5DigestAsHex(user.getPwd().getBytes());
        user.setPwd(password);
        log.info("user:{}",user);
        userMapper.insert(user);
    }

    public void updateUserInfo(UserDTO userDTO,String oldPwd){
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);
        String password = DigestUtils.md5DigestAsHex(user.getPwd().getBytes());
        //获取pwd查看是否正确
        if(!oldPwd.equals(password)){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        userDTO.setPwd(DigestUtils.md5DigestAsHex(userDTO.getPwd().getBytes()));
        BeanUtils.copyProperties(userDTO,user);
        userMapper.update(user);
    }
}
