package com.hiLunch.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hiLunch.constant.MessageConstant;
import com.hiLunch.constant.PasswordConstant;
import com.hiLunch.constant.StatusConstant;
import com.hiLunch.context.BaseContext;
import com.hiLunch.dto.EmployeeDTO;
import com.hiLunch.dto.EmployeeLoginDTO;
import com.hiLunch.dto.EmployeePageQueryDTO;
import com.hiLunch.entity.Employee;
import com.hiLunch.exception.AccountLockedException;
import com.hiLunch.exception.AccountNotFoundException;
import com.hiLunch.exception.PasswordErrorException;
import com.hiLunch.mapper.EmployeeMapper;
import com.hiLunch.result.PageResult;
import com.hiLunch.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * ログイン
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、usernameでデータを探す
        Employee employee = employeeMapper.getByUsername(username);

        //2、エラー検知
        if (employee == null) {
            //アカウントが存在しない場合
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //pwd検証
        //TODO　user側からのpwdをmd5で暗号化処理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //パスワードが合ってない場合
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //アカウントがロックされる
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、エンティティオブジェクトを返す
        return employee;
    }

    /**
     * 従業員を追加
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //属性のコピー
        BeanUtils.copyProperties(employeeDTO, employee);

        //アカウントのステータスを設定して、１は有効に（デフォルト）　0は無効に　
        employee.setStatus(StatusConstant.ENABLE);

        //pwd設定　　デフォルトは123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

//      //TODO　AOPで追加
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

    /**
     * ページングクエリ
     *
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // select * from employee limit 0,10
        //ページングクエリ始め
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);

        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 従業員のアカウントを有効化・、無効化する
     *
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        // update employee set status = ? where id = ?

        /*Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);*/

        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();

        employeeMapper.update(employee);
    }

    /**
     * ID番号でクエリ
     *
     * @param id
     * @return
     */
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 従業員情報編集
     *
     * @param employeeDTO
     */
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }
}
