package com.neu.info7205.todo.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    INVALID_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "The request is invalid"),
    UNAUTHORIZED_USER(UNAUTHORIZED, "The User is unauthorized"),
    NOT_VERIFIED(BAD_REQUEST, "User is not verified yet."),
    INVALID_TOKEN(BAD_REQUEST, "Email validation failed due to incorrect token"),
    TOKEN_EXPIRED(BAD_REQUEST, "Email validation failed due to token expiration"),
    MAIL_SENT(BAD_REQUEST, "Email has already been sent , wait for some time to resend mail verification"),
    MAIL_EMPTY(BAD_REQUEST, "Email can not be empty"),
    USER_NOT_FOUND(BAD_REQUEST, "User with this email id does not exist"),
    NO_PERMISSION(BAD_REQUEST, "You do not have permission to do this operation"),
    PASSWORD_EMPTY(BAD_REQUEST, "Password can not be empty"),
    FIRST_NAME_EMPTY(BAD_REQUEST, "First name can not be empty"),
    LAST_NAME_EMPTY(BAD_REQUEST, "Last name can not be empty"),
    INVALID_MAIL(BAD_REQUEST, "Mail is not valid"),
    EMAIL_ALREADY_EXISTS(BAD_REQUEST, "Mail already exists"),
    TASK_ID_EMPTY(BAD_REQUEST, "Task Id can not be empty"),
    LIST_ID_EMPTY(BAD_REQUEST, "List Id can not be empty"),
    TAG_NAME_EMPTY(BAD_REQUEST, "Tag name can not be empty"),
    TAG_EXISTS(BAD_REQUEST, "Tag already exists"),
    TAG_DOES_NOT_EXIST(BAD_REQUEST, "Tag does not exist"),
    NAME_EMPTY(BAD_REQUEST,"Name can nit be empty"),


    LIST_EMPTY(BAD_REQUEST,"List doen't exist"),
    LIST_NAME_EMPTY(BAD_REQUEST, "List name can not be empty"),
    TASK_SUMMARY_EMPTY(BAD_REQUEST, "Task summary cannot be empty"),
    TASK_PRIORITY_EMPTY(BAD_REQUEST, "Task Priority cannot be empty"),

    INVALID_ID(BAD_REQUEST, "Invalid Id"),
    ID_REQUIRED(BAD_REQUEST, "Id required"),
    TASK_DOESNT_EXIST(BAD_REQUEST, "Task doesn't exist, please check taskId"),
    INVALID_REQUEST_BODY(BAD_REQUEST, "Invalid request body"),
    BAD_CREDENTIALS(BAD_REQUEST, "bad credentials"),
    USER_WITH_THIS_USER_ID_DOES_NOT_EXIST(BAD_REQUEST, "User with this user Id does not exist"),
    REMINDER_LIMIT_EXCEEDED(BAD_REQUEST, "Only 5 reminders are allowed per one task"),
    ATTACHMENT_LIMIT_EXCEEDED(BAD_REQUEST, "Only 5 attachments are allowed per one task"),
    TAG_ID_EMPTY(BAD_REQUEST, "TagId cannot be Empty");

    private String msg;
    private HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus, String msg) {

        this.httpStatus = httpStatus;
        this.msg = msg;

    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
