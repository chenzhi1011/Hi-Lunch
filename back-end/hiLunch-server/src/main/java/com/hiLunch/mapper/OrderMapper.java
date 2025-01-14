package com.hiLunch.mapper;

import com.hiLunch.dto.MenuDTO;
import com.hiLunch.entity.Menu;
import com.hiLunch.vo.OrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from order where user_id = #{userId}")
    List<OrderVO> selectByUserId(Long userId);

    //TODO menu表和order表结合，查询menu—id分组后，num最大的菜品，选出name，descrip，和image
    @Select("select name,description,image from menu m left join order o on m.id = o.menu_id  ")
    Menu getBestSaler();


    void insertOrderNum(Long userId, List<MenuDTO> list, String orderNo);
}
