package com.spbt.secproject.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fail", "아이디 또는 패스워드가 틀렸습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        if (exception instanceof UsernameNotFoundException){
            hashMap.put("fail", exception.getMessage() + " 아이디는 존재하지 않습니다.");
            jsonResponse = new JSONObject(hashMap);
        } else if(exception instanceof BadCredentialsException) {
            hashMap.put("fail", "패스워드가 일치하지 않습니다.");
            jsonResponse = new JSONObject(hashMap);
        }

        objectMapper.writeValue(response.getWriter(), jsonResponse);
    }
}
