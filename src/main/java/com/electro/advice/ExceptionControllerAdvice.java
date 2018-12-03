package com.electro.advice;

import com.electro.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception e) {
        return e.getMessage();
    }


    @ExceptionHandler({UserNotFoundException.class, StreakNullException.class, StreakAuthorizeException.class, HeadlineNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleUserNotFound(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object handleExceptionAuth(Exception e) {
        return e.getMessage();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
        List<String> errors = new ArrayList<>();
        for (ObjectError objectError : error.getBindingResult().getAllErrors()) {
            errors.add(objectError.getDefaultMessage());
        }

        return errors;
    }

}
