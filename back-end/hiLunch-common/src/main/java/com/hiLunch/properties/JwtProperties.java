package com.hiLunch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hilunch.jwt")
@Data
public class JwtProperties {

    /**
     * 管理者登録の方
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * ユーザーの方
     */
    //TODO 用户端用户生成jwt令牌相关配置
//    private String userSecretKey;
//    private long userTtl;
//    private String userTokenName;

}
