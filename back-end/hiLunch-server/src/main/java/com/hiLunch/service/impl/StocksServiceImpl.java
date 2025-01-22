package com.hiLunch.service.impl;

import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.OrderDTO;
import com.hiLunch.entity.Stocks;
import com.hiLunch.exception.LessStockException;
import com.hiLunch.mapper.StocksMapper;
import com.hiLunch.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /*
     * For user: updateStock
     *
     * */
    //TODO could be optimized
    @Transactional
    public void updateStocks(List<MenuDTO> list){
        for(MenuDTO menuDTO:list) {
            Integer currentStock = stocksMapper.getStockByMenuId(menuDTO.getId());
            if(currentStock<menuDTO.getNum()){
                throw new LessStockException("sorry,the stocks is not enough");
            }
            Integer stock = currentStock-menuDTO.getNum();
            stocksMapper.setStocks(menuDTO.getId(),stock);
        }
    }

    /*
     * For admin: setStock
     *
     * */
    public void setStocks(Long menuId,Integer num){
        stocksMapper.setStocks(menuId,num);
    }



}
