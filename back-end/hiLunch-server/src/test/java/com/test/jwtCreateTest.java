package com.test;


import com.hiLunch.constant.JwtClaimsConstant;
import com.hiLunch.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class jwtCreateTest {
    @Autowired
    JwtUtil jwtUtil;
    @Test
    public void jwtCreate(){
        Map<String,Object> claim = new HashMap<>();
        Long id = 71080550L;
        claim.put(JwtClaimsConstant.ID,id);
        String jwtToken = jwtUtil.createJWT("hiLunchUser",720000000,claim);
        System.out.println(jwtToken);
    }
}
