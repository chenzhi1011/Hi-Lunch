package com.hiLunch.aspect;

import com.hiLunch.annotation.AutoFill;
import com.hiLunch.constant.AutoFillConstant;
import com.hiLunch.context.BaseContext;
import com.hiLunch.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/*
* 操作内容
*
* */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /*
    * 切入点
    * */

    @Pointcut("@annotation(com.hiLunch.annotation.AutoFill)") // 设置拦截位置
    public void autoFillPointCut(){
    }
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("共通フィールドを自動補完する。。");
        //1.現在のインターセプトされたメソッドのデータベース操作タイプを取得する（insert または　update）
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); //Method　Signature　Object
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);//方法上的注释对象
        OperationType operationType = autoFill.value();

        //2.获取当前被拦截对象的参数
        //TODO 利用反射
        Object args[] = joinPoint.getArgs();
        if (args[0] == null || args.length == 0) {
            return;
        }

        Object entity = args[0];
        //3.准备赋值数据
        LocalDateTime localDateTime = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        //4.根据当前不同操作类型，为对应的属性通过反射赋值
        if (autoFill.value() == (OperationType.INSERT)) {
            //利用反射获取方法
            //TODO 防止手动输入错误，通过常量autofillConstant来填写
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateTime.invoke(entity, localDateTime);
                setUpdateTime.invoke(entity,localDateTime);
                setCreateUser.invoke(entity,id);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (autoFill.value() == (OperationType.UPDATE)) {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                setUpdateTime.invoke(entity,localDateTime);
                setCreateUser.invoke(entity,id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
