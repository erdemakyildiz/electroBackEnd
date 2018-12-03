package com.electro.resolver;

import com.electro.exception.UserNotFoundException;
import com.electro.models.User;
import com.electro.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Value( "${secret.key}" )
    private String secretKey;

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

        String header = webRequest.getHeader("Authorization");

        if (StringUtils.isEmpty(header)) {
            Cookie electronToken = WebUtils.getCookie(webRequest.getNativeRequest(HttpServletRequest.class), "electronToken");
            if (electronToken != null) {
                String token = electronToken.getValue();
                if (!token.isEmpty()) {
                    header = "Bearer " + token;
                }
            }
        }

        final String token = header.substring(7);
        final Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String userName = claims.getSubject();

        User user = userService.findUserNickNameOrMail(userName);

        if (user == null) {
            throw new UserNotFoundException("kullanıcı bulunamadı");
        }

        return user;
    }

}
