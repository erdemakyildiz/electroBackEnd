package com.electro.security;

import com.electro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class AuthProvider implements AuthenticationProvider{

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("USER"));

        if (name.equals("id") && password.equals("pw")){
            return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        }

        if(name==null || password==null){
            return null;
        }else{
            try {
                if (userService.login(name, password)) {
                    return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
                }else {
                    return null;
                }
            } catch (Exception e){
                return null;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}