package com.ki.springbootdubboconsumerone.frame.exception;

import com.ki.springcommon.constant.CommonResDTO;
import com.ki.springcommon.constant.ConstantRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fanxuebo
 * @description 公共全局异常处理
 * @company ki
 * @createDate 2019-11-28 09:56:40 星期四
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler
    public CommonResDTO exceptionHandler(RuntimeException e) {
        e.printStackTrace();
        LOGGER.error("公共全局异常处理，错误信息:[{}]", e.toString());
        return new CommonResDTO(ConstantRes.ERROR_MSG);
    }
}
