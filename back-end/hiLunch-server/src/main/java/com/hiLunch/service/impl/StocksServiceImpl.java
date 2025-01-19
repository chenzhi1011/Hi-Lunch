package com.hiLunch.service.impl;

import com.hiLunch.entity.Stocks;
import com.hiLunch.mapper.StocksMapper;
import com.hiLunch.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocksServiceImpl implements StocksService {
    @Autowired
    StocksMapper stocksMapper;
    @Override
    public List<Stocks> checkByIds(List<Long> ids) {
        List<Stocks> list = stocksMapper.selectByIds(ids);
        return list;
    }
}
