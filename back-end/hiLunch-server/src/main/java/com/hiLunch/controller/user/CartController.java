package com.hiLunch.controller.user;

import com.hiLunch.context.BaseContext;
import com.hiLunch.dto.MenuDTO;
import com.hiLunch.entity.Menu;
import com.hiLunch.entity.Stocks;
import com.hiLunch.result.Result;
import com.hiLunch.service.StocksService;
import com.hiLunch.vo.MenuVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@ApiOperation("カートに関するAPI")
@RequestMapping("/user/cart")
public class CartController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StocksService stocksService;
    private Long userId = BaseContext.getCurrentId();
    private HashOperations<String,String ,Object> hashOperations;

    /*
    * 料理をカート追加機能
    *
    * */
    @PostMapping("/add")
    public Result add(@RequestBody List<MenuDTO> list){
        //stocks check
        List<Long> ids = new ArrayList<>();
        for(MenuDTO menu : list){
            ids.add(menu.getId());
        }
        List<Stocks> stockList = stocksService.checkByIds(ids);
        List<Long> soldId = new ArrayList<>();
        boolean soldFlag = false;
        for(Stocks stock:stockList){
            if(stock.getStock()<=0){
                soldId.add(stock.getMenuId());
                soldFlag =true;
            }
        }
        //TODO front end
        if(soldFlag){
            return Result.error(soldId.toString());
        }

        hashOperations = redisTemplate.opsForHash();
        //料理をカート追加
        String redisKey = userId.toString();
        try{
            for (MenuDTO menu:list
                 ) {
                redisKey = redisKey+":"+menu.getId();
                hashOperations.put(redisKey,"name",menu.getName());
                hashOperations.put(redisKey,"image",menu.getImage());
                hashOperations.put(redisKey,"price",menu.getPrice());
                hashOperations.put(redisKey,"num",menu.getNum());
                hashOperations.put(redisKey,"description",menu.getDescription());
            }

        }catch (Exception e){

        }
        return Result.success();
    }


    /*
     * 追加した料理数を減らす（一個づつ）
     *
     * */
    @PutMapping("/minus")
    public Result minusNum(Long menuId){
        hashOperations = redisTemplate.opsForHash();
        //menuIdでnumを取得する
        String redisKey = userId+":"+menuId;
        Integer num =(Integer) hashOperations.get(redisKey,"num");
        //numが１とすると、削除と同じにする
        if(num==1){
            return Result.success("/user/cart/delete");
        }
        //数を−１
        hashOperations.put(redisKey,"num",--num);
        return Result.success();
    }

    /*
     * 追加した料理数を増やす（一個づつ）
     *
     * */
    @PutMapping("/plus")
    public Result plusNum(Long menuId){
        hashOperations = redisTemplate.opsForHash();
        String redisKey = userId+":"+menuId;
        Integer num =(Integer) hashOperations.get(redisKey,"num");
        hashOperations.put(redisKey,"num",++num);
        return Result.success();
    }

    /*
     * カートの中の料理を削除
     *
     * */
    @DeleteMapping("/delete")
    public Result deleteByMenuId(Long menuId){
        hashOperations = redisTemplate.opsForHash();
        String redisKey = userId+":"+menuId;
        hashOperations.delete(redisKey);
        return Result.success();
    }

    /*
     * userIDでカート商品を取得する
     *
     * */
    @GetMapping
    public Result<List> getAllCartByUserId(){
        String pattern = userId+":*";
        Set<String> keys = redisTemplate.keys(pattern);
        List<MenuVO> menus = new ArrayList<>();
        if(keys==null||keys.isEmpty()){
            return Result.success(null);
        }
        for (String key:keys){
            Map<Object,Object> fields = redisTemplate.opsForHash().entries(key);
            if(fields != null && !fields.isEmpty()){
                MenuVO menuVO = mapToMenuVO(fields,key);
                if(menuVO!=null){
                    menus.add(menuVO);
                }
            }
        }
        return Result.success(menus);
    }



    private MenuVO mapToMenuVO(Map<Object, Object> fields, String key) {
        if (fields == null || fields.isEmpty()) {
            return null;
        }
        MenuVO menuVO = new MenuVO();
        menuVO.setId(parseMenuIdFromKey(key));
        menuVO.setName((String) fields.get("name"));
        menuVO.setPrice(Integer.parseInt((String) fields.get("price")));
        menuVO.setDescription((String) fields.get("description"));
        menuVO.setImage((String) fields.get("image"));
        menuVO.setNum(Integer.parseInt((String) fields.get("num")));
        return menuVO;
    }

    //キーからmenuIdを取得する（userId:menuId）
    private Long parseMenuIdFromKey(String key) {
        String[] parts = key.split(":");
        return Long.parseLong(parts[1]);
    }
}
