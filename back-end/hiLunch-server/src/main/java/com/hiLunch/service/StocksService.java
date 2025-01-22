package com.hiLunch.service;

import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.OrderDTO;
import com.hiLunch.entity.Stocks;

import java.util.List;

public interface StocksService {
    List<Stocks> checkByIds(List<Long> ids);

    void updateStocks(List<MenuDTO> list);
    void setStocks(Long menuId,Integer num);
}
