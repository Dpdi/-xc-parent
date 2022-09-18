package com.xuecheng.web.exceptions;

import com.xuecheng.commons.enums.ErrorCode;
import lombok.Data;

//自定义异常
@Data
public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }
}
