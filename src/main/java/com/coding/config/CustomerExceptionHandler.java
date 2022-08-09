package com.coding.config;

import com.coding.base.GlobalExceptionHandler;
import com.coding.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author mac
 */
@Slf4j
@RestControllerAdvice
public class CustomerExceptionHandler extends GlobalExceptionHandler {
    
    
    @ExceptionHandler(Exception.class)
    public R<String> handleBanRequest(Exception e) {
        log.error("System error", e);
        return R.createByErrorMessage("System error" + e.getMessage());
    }
}
