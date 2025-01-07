package com.hiLunch.handler;

import com.hiLunch.exception.BaseException;
import com.hiLunch.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 業務層
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("例外情報：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
