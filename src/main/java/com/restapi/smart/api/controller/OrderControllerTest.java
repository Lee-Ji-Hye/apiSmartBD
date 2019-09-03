package com.restapi.smart.api.controller;

import java.util.Map;

import com.restapi.smart.api.service.KakaoPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("api")
public class OrderControllerTest {

    public void syso(Object obj) {
        System.out.println(obj);
    }

    @GetMapping("payTest")
    public @ResponseBody Map orderTest() {
        System.out.println("mi");
        KakaoPay kakao = new KakaoPay();
        Map result = kakao.payTest();

        //System.out.println(result.get("next_redirect_app_url")+"~~@@");
        System.out.println(result.get("android_app_scheme")+"~~@@");
        //log.info("카카오페이 요청결과 : ", result.get());
        return result;
    }

    @GetMapping("kakaoPaySuccess")
    public @ResponseBody String kakaoPaySuccess() {
        System.out.println("kakaoPaySuccess");
        return null;
    }

    @GetMapping("kakaoPayCancel")
    public @ResponseBody String kakaoPayCancel() {
        System.out.println("kakaoPayCancel");
        return null;
    }

    @GetMapping("kakaoPaySuccessFail")
    public @ResponseBody String kakaoPaySuccessFail() {
        System.out.println("kakaoPaySuccessFail");
        return null;
    }

}