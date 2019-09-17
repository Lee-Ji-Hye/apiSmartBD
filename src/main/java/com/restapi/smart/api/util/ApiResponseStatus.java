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
            // 회원탈퇴 API
            case 410 : msg = "회원탈퇴 성공";
                break;
            case 411 : msg = "관리자는 권한 강등 이후 탈퇴할 수 있습니다.";
                break;
            case 419 : msg = "회원탈퇴 실패";
                break;
            // 음식점 API (500번대 사용)
            // 음식점 리스트 API
            case 501 : msg = "";
                break;

            // 음식점 스토어 API

            // 음식점 주문 API
            case 550 : msg = "주문 정보 조회 성공";
                break;
            case 551 : msg = "주문 정보가 없습니다.";
                break;
            // 음식점 주문취소 API
            case 560 : msg = "주문 취소 되었습니다.";
                break;
            case 561 : msg = "주문 정보가 없습니다."; //주문 코드(f_oid) 누락
                break;
            case 562 : msg = "카카오 결제정보가 없습니다."; //kakao TID 누락
                break;
            case 569 : msg = "주문 취소 실패되었습니다.";
                break;
            case 570 : msg = "쿠폰 발송 성공.";
                break;
            case 571 : msg = "아이디가 없습니다.";
                break;
            case 572 : msg = "쿠폰 일부 발송 성공.";
                break;
            case 579 : msg = "쿠폰 발급 실패.";
                break;
            // 주차장 API (600번대 사용)

            // 임대 API (700번대 사용)


            default: msg = "값이 없습니다.";
        }

        return msg;
    }
}
