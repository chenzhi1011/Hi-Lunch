package com.hiLunch.controller.user;

import com.hiLunch.context.BaseContext;
import com.hiLunch.dto.MenuDTO;
import com.hiLunch.entity.Menu;
import com.hiLunch.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ApiOperation("カートに関するAPI")
@RequestMapping("/user/cart")
public class CartController {
    @Autowired
    private RedisTemplate redisTemplate;
    private Long userId = BaseContext.getCurrentId();
    private HashOperations hashOperations = redisTemplate.opsForHash();

    /*
    * 料理をカート追加機能
    *
    * */
    @PostMapping("/add")
    public Result add(@RequestBody List<MenuDTO> list){
        //TODO stocks check

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
        String redisKey = userId+":"+menuId;
        return Result.success();
    }
}
