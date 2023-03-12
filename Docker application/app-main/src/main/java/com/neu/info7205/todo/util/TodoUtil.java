package com.neu.info7205.todo.util;

import com.neu.info7205.todo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class TodoUtil {
    public static String extractErrorMessage(Exception e) {
        String errorMessage = null;
        if (e instanceof SystemException) {
            SystemException se = (SystemException) e;
            errorMessage = se.getUiMessage();
            if (StringUtils.isBlank(errorMessage)) {
                ErrorCode errorCode = se.getErrorCode();
                if (errorCode != null) {
                    errorMessage = errorCode.getMsg();
                    if (StringUtils.isBlank(errorMessage)) {
                        errorMessage = errorCode.getMsg();
                    }
                }
            }
            if (StringUtils.isBlank(errorMessage) && StringUtils.isNotBlank(se.getErrorMessage())) {
                errorMessage = se.getErrorMessage();
            }
        }

        if (StringUtils.isBlank(errorMessage)) {
            errorMessage = "Unexpected error occurred .Please contact system administrator";
        }
        return errorMessage;
    }

}

