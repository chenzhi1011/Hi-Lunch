package com.hiLunch.controller.user;

import com.hiLunch.result.Result;
import com.hiLunch.service.MenuService;
import com.hiLunch.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/homepage")
public class HomepageController {
    @Autowired
    MenuService menuService;
    /*
     * ホーム画面に今日のベストセーラー３
     *
     * */
    @GetMapping("/recommend/{weekday}")
    public Result<List> SalesTop3(@PathVariable Integer weekday){
        List<MenuVO> list = menuService.getTop3(weekday);
        return Result.success(list);
    }

    /*
     * 今まで一番人気の料理
     *
     * */
    @GetMapping("/bestsaler")
    public Result<MenuVO> getBestSaler(){
        MenuVO menuVO=menuService.getBestSaler();
        return Result.success(menuVO);
    }
}
