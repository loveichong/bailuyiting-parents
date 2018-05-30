package com.bailuyiting.commons.until;

/**
 * Created by Administrator on 2017/9/13.
 */
public class RestException extends  RuntimeException {
    private Integer statusCode=400;
    public RestException() {
    }

    public RestException(String message) {
        super(message);
    }
    public RestException(Integer statusCode,String message) {
        super(message);
        if(statusCode!=null)
        this.statusCode=statusCode;
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestException(Throwable cause) {
        super(cause);
    }

    public RestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
