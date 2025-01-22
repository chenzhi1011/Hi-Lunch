package com.hiLunch.controller.user;


import com.hiLunch.entity.Stocks;
import com.hiLunch.result.Result;
import com.hiLunch.service.MenuService;
import com.hiLunch.service.StocksService;
import com.hiLunch.vo.MenuVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userMenuController")
@Api(tags="ユーザー側のメニューに関する")
@RequestMapping("/user/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    StocksService stocksService;

    /*
     * show today'menu
     *
     * */
    @GetMapping("/{weekday}")
    public Result<List> getMenuByday(@PathVariable Integer weekday){
        List<MenuVO> list = menuService.getMenuByDay(weekday);
        return Result.success(list);
    }


    @GetMapping("/stocks")
    public Result<List> getStocks(List<Long> ids){
        List<Stocks> stocksList = stocksService.checkByIds(ids);
        return Result.success(stocksList);
    }

}
