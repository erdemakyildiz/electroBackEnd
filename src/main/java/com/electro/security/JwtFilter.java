package com.electro.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.util.WebUtils;

public class JwtFilter extends GenericFilterBean {

    private String secretKey;

    List<String> allowURL = new ArrayList<>();

    public JwtFilter(String secretKey) {
        allowURL.add("/streak/streaks");
        allowURL.add("/streak/categories");
        allowURL.add("/user/auth");
        allowURL.add("/user/create");

        this.secretKey = secretKey;
    }

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {

            if (allowURL.contains(request.getRequestURI())){
                chain.doFilter(req, res);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (StringUtils.isEmpty(authHeader)) {
                Cookie electronToken = WebUtils.getCookie(request, "electronToken");
                if (electronToken != null) {
                    String token = electronToken.getValue();
                    if (!token.isEmpty()) {
                        authHeader = "Bearer " + token;
                    }
                }
            }

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            try {
                final Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token");
            }

            chain.doFilter(req, res);
        }
    }
}