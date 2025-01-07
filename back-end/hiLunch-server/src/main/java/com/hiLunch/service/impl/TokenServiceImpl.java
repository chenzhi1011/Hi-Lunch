package com.hiLunch.service.impl;

import com.hiLunch.entity.PersistToken;
import com.hiLunch.mapper.TokenMapper;
import com.hiLunch.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenMapper tokenMapper;
    @Override
    public void insert(String token) {
        PersistToken persistToken = new PersistToken();
        persistToken.setSecretToken(token);
        persistToken.setCreateTime(LocalDateTime.now());
        tokenMapper.insert(persistToken);
    }

    @Override
    public void delete(String token) {
        tokenMapper.delete(token);
    }
}
