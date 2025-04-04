package com.hiLunch.controller.admin;


import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.MenuPageQueryDTO;
import com.hiLunch.result.PageResult;
import com.hiLunch.result.Result;
import com.hiLunch.service.AwsService;
import com.hiLunch.service.MenuService;
import com.hiLunch.service.StocksService;
import com.hiLunch.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


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
    @Autowired
    private AwsService awsService;
    @Autowired
    private StocksService stocksService;

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
    @PostMapping("/add")
    @ApiOperation("料理追加")
    public Result save(@RequestBody MenuDTO menuDTO) {
        log.info("料理追加：{}", menuDTO);
        menuService.addMenu(menuDTO);

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


    /**
     * 料理のstockを設定
     *
     * @param stock
     *
     */
    @PutMapping("/stock")
    public Result setStocks(Long menuId, Integer stock){
        stocksService.setStocks(menuId,stock);
        return Result.success();
    }

    /**
     * 料理の画像をアップロード
     * @param
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info(file.toString());
        String url = awsService.upLoadFile(file);
        return Result.success(url);
    }

}
