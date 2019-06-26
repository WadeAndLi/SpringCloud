package com.wade.common;

public class ExceptionPO {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionPO(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getStatusCode();
        this.message = exceptionEnum.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
