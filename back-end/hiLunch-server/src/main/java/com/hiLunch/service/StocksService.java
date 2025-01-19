package com.hiLunch.service;

import com.hiLunch.entity.Stocks;

import java.util.List;

public interface StocksService {
    List<Stocks> checkByIds(List<Long> ids);
}
