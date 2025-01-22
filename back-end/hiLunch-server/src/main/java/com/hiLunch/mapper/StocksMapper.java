package com.hiLunch.mapper;

import com.hiLunch.annotation.AutoFill;
import com.hiLunch.entity.Stocks;
import com.hiLunch.enumeration.OperationType;
import com.hiLunch.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface StocksMapper {
    List<Stocks> selectByIds(List<Long> ids);


    @Select("select stock from stocks where menu_id=#{menuId} for update ")
    Integer getStockByMenuId(Long menuId);

    @Update("update stocks set stock=#{num} where menu_id=#{menuId}")
    void setStocks(Long menuId, Integer num);
}
