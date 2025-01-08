package com.hiLunch.controller.user;

import com.hiLunch.dto.OrderDTO;
import com.hiLunch.result.Result;
import com.hiLunch.service.OrderService;
import com.hiLunch.vo.OrderVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order")
@Api(tags="注文した料理に関する")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;


    /**
     * 　注文内容をgetする
     * @param
     * @return
     */
    @GetMapping
    public Result<List> getAllOrder(){
        //get info from service
        //TODO 返回前端的时候，相同订单号分组展示问题
        List<OrderVO> list = orderService.getAllOrder();
        return Result.success(list);
    }

    /**
     * 新しい注文がした時
     * @param list
     *
     */
//    @PostMapping("/create")
    //TODO 和购物车的CRUD一起考虑
    // 购物车是redis还是什么形式？从购物车加入，还是传数据到后端
//    public Result createOrder(@RequestBody List<OrderDTO> list){
//        orderService.createOrder(list);
//        return Result.success();
//    }


}
