package com.hiLunch.controller.admin;


import com.hiLunch.constant.JwtClaimsConstant;
import com.hiLunch.dto.EmployeeDTO;
import com.hiLunch.dto.EmployeeLoginDTO;
import com.hiLunch.dto.EmployeePageQueryDTO;
import com.hiLunch.entity.Employee;
import com.hiLunch.properties.JwtProperties;
import com.hiLunch.result.PageResult;
import com.hiLunch.result.Result;
import com.hiLunch.service.EmployeeService;
import com.hiLunch.utils.JwtUtil;
import com.hiLunch.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 従業員管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * ログイン
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("employee login：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //ログイン検証ができれば jwt token生成
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * ログアウト
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 従業員追加
     * @param employeeDTO
     * @return
     */
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * ページングクエリ
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("ページングクエリ：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 従業員のアカウントを有効化・無効化する
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("従業員のアカウントを有効化・無効化する：{},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * ID番号でクエリ
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 従業員情報編集
     * @param employeeDTO
     * @return
     */
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("従業員情報編集：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }


}
