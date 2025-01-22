package com.hiLunch.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.MenuPageQueryDTO;
import com.hiLunch.entity.Menu;
import com.hiLunch.exception.DeletionNotAllowedException;
import com.hiLunch.mapper.MenuMapper;
import com.hiLunch.mapper.OrderMapper;
import com.hiLunch.mapper.StocksMapper;
import com.hiLunch.result.PageResult;
import com.hiLunch.service.MenuService;
import com.hiLunch.constant.MessageConstant;
import com.hiLunch.constant.StatusConstant;
import com.hiLunch.vo.MenuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private StocksMapper stocksMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 料理追加
     *
     * @param menuDTO
     */
    @Transactional
    public void addMenu(MenuDTO menuDTO) {

        Menu menu = new Menu();

        BeanUtils.copyProperties(menuDTO, menu);

        menuMapper.insert(menu);

    }

    /**
     * 料理をページングでクエリ
     *
     * @param menuPageQueryDTO
     * @return
     */
    public PageResult pageQuery(MenuPageQueryDTO menuPageQueryDTO) {
        PageHelper.startPage(menuPageQueryDTO.getPage(), menuPageQueryDTO.getPageSize());
        Page<MenuVO> page = menuMapper.pageQuery(menuPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 選択した料理を一括で削除
     *
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //販売中かどうかの判断
        for (Long id : ids) {
            Menu menu = menuMapper.getById(id);
            if (menu.getIsSale() == StatusConstant.ENABLE) {
                //販売中なので、削除できない
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        for (Long id : ids) {
            menuMapper.deleteById(id);
        }
    }

    /**
     * IDで料理を照会
     *
     * @param id
     * @return
     */
    public MenuVO getById(Long id) {
        Menu menu = menuMapper.getById(id);
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menu, menuVO);
        return menuVO;
    }

    /**
     * IDで料理情報を編集
     *
     * @param menuDTO
     */
    public void update(MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        menuMapper.update(menu);
    }

    /**
     * 料理の販売の状態を設定
     *
     * @param status
     * @param id
     */
    @Transactional
    public void startOrStop(Integer status, Long id) {
        Menu menu = Menu.builder()
                .id(id)
                .isSale(status)
                .build();
        menuMapper.update(menu);

    }

    /*
    * user
    * ホーム画面に今日のベストセーラー３
    *
    * */
    public List<MenuVO> getTop3(Integer weekday){
        List<MenuVO> list  = menuMapper.getLeastThree(weekday);

        return list;
    }

    /*
     * 今まで一番人気の料理
     *
     * */
    @Override
    public MenuVO getBestSaler() {
        Menu menu = orderMapper.getBestSaler();
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menu,menuVO);
        return menuVO;
    }

    /*
     * show menu by day
     *
     * */
    @Override
    public List<MenuVO> getMenuByDay(Integer weekday) {
        List<MenuVO> list = menuMapper.getMenuByDay(weekday);
        return list;
    }


}
