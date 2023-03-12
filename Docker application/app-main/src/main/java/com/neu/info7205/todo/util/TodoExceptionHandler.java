package com.neu.info7205.todo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class TodoExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handle(Exception e) {
        log.error("Error occured:", e);

        String errorMessage = null;
        ErrorCode errorCode = null;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof SystemException) {
            SystemException se = (SystemException) e;
            errorCode = se.getErrorCode();
            if (errorCode != null) {

                    httpStatus = errorCode.getHttpStatus();

                errorMessage = TodoUtil.extractErrorMessage(se);
            }

        }
        if (StringUtils.isBlank(errorMessage)) {
            errorMessage = "Unexpected error occurred .Please contact system administrator";
        }

        return ResponseEntity
                .status(httpStatus)
                .body(new ResponseObj(false, null, errorMessage));
    }
}
