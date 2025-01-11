package com.hiLunch.controller.user;


import com.hiLunch.result.Result;
import com.hiLunch.service.MenuService;
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

    /*
     * show today'menu
     *
     * */
    @GetMapping
    public Result<List> getMenuByday(@PathVariable Integer weekday){
        List<MenuVO> list = menuService.getMenuByDay(weekday);
        return Result.success(list);
    }

    //TODO stocks並行問題の処理
//    public Result<Integer> getStocks(){}

}
