package com.hiLunch.mapper;

import com.github.pagehelper.Page;
import com.hiLunch.annotation.AutoFill;
import com.hiLunch.dto.EmployeePageQueryDTO;
import com.hiLunch.entity.Employee;
import com.hiLunch.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * usernameで従業員を探す
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     *　従業員データ挿入
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, create_time, update_time, create_user, update_user,status) " +
            "values " +
            "(#{name},#{username},#{password},#{phone},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    @AutoFill(OperationType.INSERT)
    void insert(Employee employee);

    /**
     * ページングクエリ
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据主键动态修改属性
     * @param employee
     */
    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    /**
     * ID番号でクエリ
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
