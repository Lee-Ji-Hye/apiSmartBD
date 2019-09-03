package com.restapi.smart.controllers;



import com.restapi.smart.security.token.PostAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    //permitall이여서 인증없이 http 이루어졌으나, 인증객체가없어 nullpoint exception발생
    @GetMapping("/hello")
    public String getUsername(Authentication authentication) {

        PostAuthorizationToken token = (PostAuthorizationToken)authentication;

        return token.getAccountContext().getUsername();
    }

    @GetMapping("/chkhello")
    public String getUsername() {

        return "정상동작chk";
    }

}