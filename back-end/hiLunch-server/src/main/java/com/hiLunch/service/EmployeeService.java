package com.hiLunch.service;

import com.hiLunch.dto.EmployeeDTO;
import com.hiLunch.dto.EmployeeLoginDTO;
import com.hiLunch.dto.EmployeePageQueryDTO;
import com.hiLunch.entity.Employee;
import com.hiLunch.result.PageResult;

public interface EmployeeService {

    /**
     * 従業員ログイン
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 従業員追加
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * ページングクエリ
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 従業員のアカウントを有効化・無効化する
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * ID番号でクエリ
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 従業員情報編集
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
