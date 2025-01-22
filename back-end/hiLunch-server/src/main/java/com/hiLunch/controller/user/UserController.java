package com.hiLunch.controller.user;

import com.hiLunch.constant.JwtClaimsConstant;
import com.hiLunch.context.BaseContext;
import com.hiLunch.dto.UserDTO;
import com.hiLunch.entity.User;
import com.hiLunch.properties.JwtProperties;
import com.hiLunch.result.Result;
import com.hiLunch.service.TokenService;
import com.hiLunch.service.UserService;
import com.hiLunch.utils.JwtUtil;
import com.hiLunch.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "ユーザーに関する")
public class UserController {
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    @ApiOperation(value = "user login")
    public Result<UserVO> login(UserDTO userDTO){
        log.info("ユーザーID:{}",userDTO.getId());
        //dbからアカウント情報が正しいかどうかを判断する
        User user = userService.login(userDTO);

        //アカウントがあれば、jwtトークンを生成して、発行する
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID,user.getId());
        String jwtToken = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .pwd(user.getPwd())
                .email(user.getEmail())
                .department(user.getDepartment())
                .token(jwtToken)
                .build();
        //TODO 登陆成功把token持久化
        tokenService.insert(jwtToken);
        //TODO 返回给前端的userVO对象如何解析
        return Result.success(userVO);
    }

    @PostMapping("/signup")
    public Result signUp(@RequestBody UserDTO userDTO){
        userService.signup(userDTO);
        return Result.success();
    }

    @PutMapping("/pwdreset")
    //TODO 重要信息暴露在url中?
    public Result pwdReset(@RequestBody UserDTO userDTO,String oldPwd){
        userService.updateUserInfo(userDTO,oldPwd);
        return Result.success();
    }

    @PutMapping("/logout")
    public Result logout(){
        tokenService.delete(BaseContext.getToken());
        return Result.success();
    }

}
