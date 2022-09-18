package com.xuecheng.web.exceptions;

import com.xuecheng.commons.enums.ErrorCode;
import com.xuecheng.commons.model.vo.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理器
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e) {
        e.printStackTrace();
        return ResponseResult.errorResult(ErrorCode.ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseResult businessException(BusinessException e) {
        e.printStackTrace();
        ErrorCode errorCode = e.getErrorCode();
        return ResponseResult.errorResult(errorCode);
    }
}
