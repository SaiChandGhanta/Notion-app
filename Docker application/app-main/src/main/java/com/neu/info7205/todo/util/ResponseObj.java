package com.neu.info7205.todo.util;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.HashMap;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Slf4j
public class ResponseObj {

    private boolean result;

    private String errorDesc;

    private String requestId;

    private Object data;

    private String message;

    public ResponseObj() {
        this.requestId = MDC.get("requestId");
    }



    public
    ResponseObj(boolean result, Object data) {
        this(result, data,null );
    }

    public ResponseObj(boolean result, String errorDesc) {
        this(result, (Object) null, errorDesc);
    }

    public ResponseObj(boolean result, Object data, String errorDesc) {
        this.requestId = MDC.get("requestId");
        this.result = result;
        this.data = data;
        this.errorDesc = errorDesc;
    }

    public ResponseObj(boolean result, Object data, String message,String errorDesc) {
        this.requestId = MDC.get("requestId");
        this.result = result;
        this.data = data;
        this.message = message;
        this.errorDesc=errorDesc;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }


    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "ResponseObj{result=" + this.result +  ", errorDesc='" + this.errorDesc + '\'' + ", requestId='" + this.requestId + '\'' + ", data=" + this.data + ", message='" + this.message + '\'' + '}';
    }
}
