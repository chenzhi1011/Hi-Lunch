package com.hiLunch.service;

import com.hiLunch.dto.MenuDTO;
import com.hiLunch.vo.MenuVO;
import com.hiLunch.vo.OrderVO;

import java.util.List;

public interface OrderService {
    List<OrderVO> getAllOrder();

    void cashOrderNum(List<MenuDTO> list , String orderNum);
}
