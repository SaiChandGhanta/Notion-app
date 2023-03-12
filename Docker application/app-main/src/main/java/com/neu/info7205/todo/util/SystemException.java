package com.neu.info7205.todo.util;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;
    private final Map<String, Object> properties;
    private ExceptionType exceptionType;
    private int httpStatus;
    private String description;
    private boolean traceEnabled;
    private String uiMessage;


    public SystemException(ErrorCode errorCode, int httpStatus) {
        this((String) null, (Throwable) null, errorCode, httpStatus);
    }

    public SystemException(String message) {
        this(message, (Throwable) null, (ErrorCode) null);
    }

    public SystemException(String message, int httpStatus) {
        this((String) message, (ErrorCode) null, httpStatus);
    }

    public SystemException(String message, Throwable cause) {
        this(message, cause, (ErrorCode) null);
    }

    public SystemException(String message, ErrorCode errorCode) {
        this(message, (Throwable) null, errorCode);
    }

    public SystemException(String message, ErrorCode errorCode, int httpStatus) {
        this(message, (Throwable) null, errorCode, httpStatus);
    }

    public SystemException(Throwable cause, ErrorCode errorCode) {
        this((String) null, cause, errorCode);
    }

    public SystemException(Throwable cause, ErrorCode errorCode, int httpStatus) {
        this((String) null, cause, errorCode, httpStatus);
    }

    public SystemException(String message, Throwable cause, ErrorCode errorCode) {
        this(message, cause, errorCode, 500);
    }

    public SystemException(String message, Throwable cause, ErrorCode errorCode, int httpStatus) {
        super(message, cause);
        this.traceEnabled = true;
        this.properties = new HashMap<>();
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public static SystemException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof SystemException) {
            SystemException se = (SystemException) exception;
            return errorCode != null && errorCode != se.getErrorCode() ? new SystemException(exception.getMessage(), exception, errorCode) : se;
        } else {
            return new SystemException(exception.getMessage(), exception, errorCode);
        }
    }

    public static SystemException wrap(Throwable exception) {
        return wrap(exception, (ErrorCode) null);
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        if (StringUtils.isNotBlank(this.uiMessage)) {
            return this.uiMessage;
        } else {
            return this.getErrorCode() != null ? this.getErrorCode().name() : null;
        }
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isTraceEnabled() {
        return this.traceEnabled;
    }

    public SystemException httpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public SystemException description(String description) {
        this.description = description;
        return this;
    }

    public SystemException disableTrace() {
        this.traceEnabled = false;
        return this;
    }

    public SystemException exceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
        return this;
    }

    private ExceptionType getExceptionType() {
        if (this.exceptionType == null) {

            if (this.exceptionType == null) {
                this.exceptionType = ExceptionType.DEFAULT;
            }
        }

        return this.exceptionType;
    }

    public <T> T get(String name) {
        return (T) this.properties.get(name);
    }

    public SystemException set(String name, Object value) {
        this.properties.put(name, value);
        return this;
    }

    public String getUiMessage() {
        return this.uiMessage;
    }

    public void setUiMessage(String uiMessage) {
        this.uiMessage = uiMessage;
    }

    public String getLocalizedMessage() {
        StringBuilder errorMessage = new StringBuilder("\n\t-------------------------------");
        if (this.getErrorCode() != null) {
            errorMessage.append("\n\t ").append(this.getErrorCode()).append(" : ").append(this.getErrorCode().getClass().getName());
            errorMessage.append("\n\t Message : ").append(this.getErrorCode().name());
        }

        Iterator var2 = this.properties.keySet().iterator();

        while (var2.hasNext()) {
            String key = (String) var2.next();
            errorMessage.append("\n\t ").append(key).append("=[").append(this.properties.get(key)).append("]");
        }

        errorMessage.append("\n\t-------------------------------");
        String localizedMessage = super.getLocalizedMessage();
        if (StringUtils.isNotBlank(localizedMessage)) {
            errorMessage.append("\n").append(localizedMessage);
        }

        return errorMessage.toString();
    }

    public void emitLog(Logger logger) {
        this.emitLog(this.getMessage(), logger, false);
    }

    public void emitLog(String desc, Logger logger) {
        this.emitLog(desc, logger, true);
    }

    private void emitLog(String desc, Logger logger, boolean appendMessage) {
        String message = appendMessage ? this.getMessage() + ", " + this.getLongMessage(desc) : this.getLongMessage(desc);
        if (this.isTraceEnabled()) {
            logger.error(message, this);
        } else {
            logger.warn(message);
        }

    }

    public String getLongMessage() {
        return this.getLongMessage(this.getMessage());
    }

    public String getLongMessage(String desc) {
        String message = "eType=" + this.getExceptionType().getName() + ", code=" + this.getErrorCode();
        if (desc != null) {
            message = desc + ", " + message;
        }

        if (this.getDescription() != null) {
            message = message + ", desc=" + this.getDescription();
        }

        if (StringUtils.isNotBlank(this.uiMessage)) {
            message = message + " Message: " + this.uiMessage;
        }

        if (!this.properties.isEmpty()) {
            message = message + ", Properties: " + this.properties;
        }

        return message;
    }

    public String toString() {
        return this.getErrorCode() != null ? "eType=" + this.getExceptionType().getName() + ", code=" + this.getErrorCode() + ", " + super.toString() : super.toString();
    }
}