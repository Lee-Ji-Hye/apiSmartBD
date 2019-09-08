package com.restapi.smart.controllers;


import com.restapi.smart.security.token.PostAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/memApi")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/hello")
    public String getUsername(Authentication authentication) {

        PostAuthorizationToken token = (PostAuthorizationToken)authentication;

        return token.getAccountContext().getUsername();
    }

//    @PostMapping("/hello2")
//    public String getUsername2(Authentication authentication) {
//        String mimi = "";
//        PostAuthorizationToken token = (PostAuthorizationToken)authentication;
//        if(token == null) {
//            System.out.println("비회원");
//            mimi = "비회원";
//        } else {
//            System.out.println("회원");
//            mimi = "회원";
//        }
//        return mimi;
//    }

}