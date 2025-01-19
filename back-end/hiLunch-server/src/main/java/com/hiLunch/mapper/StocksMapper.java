package com.hiLunch.mapper;

import com.hiLunch.entity.Stocks;
import com.hiLunch.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StocksMapper {
    List<Stocks> selectByIds(List<Long> ids);
}
