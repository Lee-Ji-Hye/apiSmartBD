package com.restapi.smart.api.util;

import org.springframework.stereotype.Component;

@Component
public class ApiResponseStatus {

    public String responseMsg(int stateCode) {
        String msg = "";

        switch (stateCode) {
            // 회원가입 API
            case 300 : msg = "회원가입성공";
                break;
            case 301 : msg = "유효하지 않은값입니다."; //파라미터 부재
                break;
            case 302 : msg = "아이디가 없습니다."; //아이디부재
                break;
            case 303 : msg = "유효하지 않은 비밀번호입니다."; //비밀번호부재
                break;
            case 304 : msg = "이메일을 입력하세요";
                break;
            case 305 : msg = "핸드폰번호를 입력하세요";
                break;
            case 306 : msg = "존재하는 아이디가 있습니다.";
                break;
            case 399 : msg = "회원가입 실패";
                break;
            // 로그인 API
            case 400 : msg = "로그인 성공";
                break;
            case 401 : msg = "유효하지 않은값입니다."; //파라미터 부재
                break;
            case 402 : msg = "아이디가 없습니다."; //아이디부재
                break;
            case 403 : msg = "유효하지 않은 비밀번호입니다."; //비밀번호부재
                break;
            case 404 : msg = "존재하지 않는 아이디입니다";
                break;
            case 405 : msg = "비밀번호가 일치하지 않습니다.";
                break;
            case 499 : msg = "로그인 실패";
                break;


            default: msg = "값이 없습니다.";
        }

        return msg;
    }
}
