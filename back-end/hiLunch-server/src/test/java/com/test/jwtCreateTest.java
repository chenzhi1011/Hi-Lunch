package com.test;


import com.hiLunch.constant.JwtClaimsConstant;
import com.hiLunch.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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



    public class ValidatorTest {
        public static void main(String[] args) {
            try {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                System.out.println("Validator loaded successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
