package com.hiLunch.controller.admin;


import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.MenuPageQueryDTO;
import com.hiLunch.result.PageResult;
import com.hiLunch.result.Result;
import com.hiLunch.service.MenuService;
import com.hiLunch.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * メニュー管理
 */
@RestController
@RequestMapping("/admin/menu")
@Api(tags = "メニューに関するAPI")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 料理をページングでクエリ
     *
     * @param menuPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("料理をページングでクエリ")
    public Result<PageResult> page(MenuPageQueryDTO menuPageQueryDTO) {
        log.info("料理をページングでクエリ:{}", menuPageQueryDTO);
        PageResult pageResult = menuService.pageQuery(menuPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 料理追加
     *
     * @param menuDTO
     * @return
     */
    @PostMapping
    @ApiOperation("料理追加")
    public Result save(@RequestBody MenuDTO menuDTO) {
        log.info("料理追加：{}", menuDTO);
        menuService.addMenu(menuDTO);

        //TODO 清理redis缓存数据
//        String key = "dish_" + menuDTO.getCategory();
//        cleanCache(key);
        return Result.success();
    }



    /**
     * 選択した料理を一括で削除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("選択した料理を一括で削除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("選択した料理を一括で削除：{}", ids);
        menuService.deleteBatch(ids);

        //TODO redis

        return Result.success();
    }

    /**
     * IDで料理を照会
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("IDで料理を照会")
    public Result<MenuVO> getById(@PathVariable Long id) {
        log.info("IDで料理を照会：{}", id);
        MenuVO menuVO = menuService.getById(id);
        return Result.success(menuVO);
    }

    /**
     * IDで料理情報を編集
     *
     * @param menuDTO
     * @return
     */
    @PutMapping
    @ApiOperation("IDで料理情報を編集")
    public Result update(@RequestBody MenuDTO menuDTO) {
        log.info("IDで料理情報を編集：{}", menuDTO);
        menuService.update(menuDTO);
        //TODO aop
        return Result.success();
    }

    /**
     * 料理の販売の状態を設定
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("料理の販売の状態を設定")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        menuService.startOrStop(status, id);
    //TODO redis
        return Result.success();
    }

    //TODO stocks設定API　まだかんせいしていない
}
