package com.electro;

import com.electro.security.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElectroApplication extends SpringBootServletInitializer {

    @Value("${secret.key}")
    private String secretKey;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(secretKey));
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(ElectroApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ElectroApplication.class, args);
    }

}
