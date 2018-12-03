package com.electro.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class RequestWrapper extends HttpServletRequestWrapper {

    public Map customHeaders = new HashMap();

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String getHeader(String name) {
        String header = super.getHeader(name);
        Object token = customHeaders.get("Authorization");
        if (token != null) {
            String tokenString = token.toString();
            if (!tokenString.isEmpty())
                return "Bearer " + tokenString;
        }

        return (header != null) ? header : super.getParameter(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Enumeration enumeration = super.getHeaders(name);
        if (!enumeration.hasMoreElements()){
            Object token = customHeaders.get("Authorization");
            if (token != null) {
                String tokenString = token.toString();
                if (!tokenString.isEmpty()){
                    Set<String> strings = new HashSet<>();
                    strings.add("Bearer " + tokenString);
                    enumeration = new Vector(strings).elements();
                }

            }
        }

        return enumeration;
    }

}
