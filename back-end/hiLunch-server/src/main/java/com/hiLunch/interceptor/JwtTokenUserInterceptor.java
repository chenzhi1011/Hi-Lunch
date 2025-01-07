package com.hiLunch.interceptor;

import com.hiLunch.constant.JwtClaimsConstant;
import com.hiLunch.context.BaseContext;
import com.hiLunch.properties.JwtProperties;
import com.hiLunch.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt検証のためのインターセプター
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * jwt検証
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader(jwtProperties.getUserTokenName());

        try {
            log.info("jwt:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long Id = Long.valueOf(claims.get(JwtClaimsConstant.ID).toString());
            log.info("user_id：", Id);
            //TODO 使每一个请求的后续操作里都有这个id
            BaseContext.setCurrentId(Id);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }
}
