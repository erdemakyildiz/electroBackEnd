package com.electro.advice;

import lombok.Data;

import java.util.Date;

@Data
public class Response {

    private Object data;
    private String requestId;
    private Date timestamp;

}