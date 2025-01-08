package com.hiLunch.mapper;

import com.hiLunch.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockMapper {
    @Select("select menu_id from")
    List<Long> getLeastThree();
}
