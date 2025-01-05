package com.hiLunch.service;

import com.hiLunch.dto.MenuDTO;
import com.hiLunch.dto.MenuPageQueryDTO;
import com.hiLunch.result.PageResult;
import com.hiLunch.vo.MenuVO;

import java.util.List;

public interface MenuService {

    /**
     * 料理をページングでクエリ
     *
     * @param menuPageQueryDTO
     * @return
     */
    PageResult pageQuery(MenuPageQueryDTO menuPageQueryDTO);

    /**
     * 料理追加
     *
     * @param menuDTO
     */
    public void addMenu(MenuDTO menuDTO);

    /**
     * 選択した料理を一括で削除
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * IDで料理を照会
     *
     * @param id
     * @return
     */
    MenuVO getById(Long id);

    /**
     * IDで料理情報を編集
     *
     * @param menuDTO
     */
    void update(MenuDTO menuDTO);

    /**
     * 料理の販売の状態を設定
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

}
