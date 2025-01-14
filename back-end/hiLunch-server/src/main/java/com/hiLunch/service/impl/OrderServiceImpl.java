package com.hiLunch.service.impl;

import com.hiLunch.context.BaseContext;
import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.OrderDTO;
import com.hiLunch.mapper.OrderMapper;
import com.hiLunch.service.OrderService;
import com.hiLunch.vo.MenuVO;
import com.hiLunch.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    /**
     * 　注文内容をgetする
    * @param
    * @return
    */
    @Override
    public List<OrderVO> getAllOrder() {
        Long userId = BaseContext.getCurrentId();
        List<OrderVO> list = orderMapper.selectByUserId(userId);
        return list;
    }

    @Override
    public void cashOrderNum(List<MenuDTO> list, String orderNo) {
        Long userId = BaseContext.getCurrentId();
        orderMapper.insertOrderNum(userId,list,orderNo);
    }
    /**
     * 　新しい注文がした時
     * @param
     * @return
     */
//    public void createOrder(List<OrderDTO> list){
//        orderMapper.insert();
//    }

}
