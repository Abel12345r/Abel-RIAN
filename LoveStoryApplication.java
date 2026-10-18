
package com.lovestory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@SpringBootApplication
public class LoveStoryApplication {
    @Bean
    FilterRegistrationBean<Filter> apiGuard() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter((request, response, chain) -> {
            HttpServletRequest req=(HttpServletRequest)request;
            HttpServletResponse res=(HttpServletResponse)response;
            res.setHeader("Cache-Control","no-store");
            res.setHeader("X-Content-Type-Options","nosniff");
            res.setHeader("Referrer-Policy","same-origin");
            if(req.getRequestURI().startsWith("/api/") && !req.getMethod().equals("GET") && !req.getRequestURI().equals("/api/auth/login")) {
                HttpSession session=req.getSession(false);
                String expected=session==null?null:(String)session.getAttribute("csrf");
                if(expected==null || !expected.equals(req.getHeader("X-Love-CSRF"))) { res.sendError(403,"CSRF validation failed"); return; }
            }
            chain.doFilter(request,response);
        });
        bean.addUrlPatterns("/api/*"); bean.setOrder(1); return bean;
    }
    public static void main(String[] args) {
        SpringApplication.run(
            LoveStoryApplication.class, args
        );
    }
}
