package com.hiLunch.service.impl;

import com.hiLunch.mapper.TokenMapper;
import com.hiLunch.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenMapper tokenMapper;
    @Override
    public void insert(String token) {
        tokenMapper.insert(token);
    }

    @Override
    public void delete(String token) {
        tokenMapper.delete(token);
    }
}
