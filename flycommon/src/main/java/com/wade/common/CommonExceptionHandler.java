package com.wade.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler  {

    @ExceptionHandler(FlyException.class)
    public static ResponseEntity<ExceptionPO> handleException(FlyException exception) {
        return ResponseEntity.status(exception.getExceptionEnum().getStatusCode())
                .body(new ExceptionPO(exception.getExceptionEnum()));
    }
}
