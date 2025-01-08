package com.hiLunch.mapper;

import com.github.pagehelper.Page;
import com.hiLunch.dto.MenuPageQueryDTO;
import com.hiLunch.entity.Menu;
import com.hiLunch.vo.MenuVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {

    /**
     * 料理をページングでクエリ
     *
     * @param menuPageQueryDTO
     * @return
     */
    Page<MenuVO> pageQuery(MenuPageQueryDTO menuPageQueryDTO);


    /**
     * 料理追加
     *
     * @param menu
     */
    //TODO AOP
    void insert(Menu menu);


    /**
     * IDで料理を照会
     *
     * @param id
     * @return
     */
    @Select("select * from menu where id = #{id}")
    Menu getById(Long id);

    /**
     * IDで料理を削除
     *
     * @param id
     */
    @Delete("delete from menu where id = #{id}")
    void deleteById(Long id);

    /**
     * IDで料理情報を編集
     *
     * @param menu
     */
    //TODO aop
    void update(Menu menu);

    /**
     * 動的料理情報を探す
     *
     * @param menu
     * @return
     */
    List<Menu> list(Menu menu);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);


    //TODO 複雑なSQL　　練習
@Select("select name,description,image from menu m left join stocks s on m.id=s.menu_id" +
        " where weekday=#{weekday} and is_sale=1 and stock!=0 order by stock asc limit 3")
    List<MenuVO> getLeastThree(Integer weekday);
}
