package com.electro.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Date;
import java.util.UUID;

@RestControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Class<?> parameterType = returnType.getParameterType();
        return !parameterType.equals(String.class) && !parameterType.equals(ResponseEntity.class);
    }


    @Nullable
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse resp) {

        Response response = new Response();
        response.setData(body);
        response.setTimestamp(new Date());
        response.setRequestId(UUID.randomUUID().toString());

        return response;

    }

}