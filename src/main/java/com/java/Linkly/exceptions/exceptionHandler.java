package com.java.Linkly.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class exceptionHandler {

    @ExceptionHandler(ShortUrlNotFoundException.class)
    String handleShortUrlNotFoundException(ShortUrlNotFoundException e){
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    String handleException(Exception e){
        return "error/500";
    }
}
