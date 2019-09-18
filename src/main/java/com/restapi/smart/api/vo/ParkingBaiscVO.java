package com.restapi.smart.api.vo;

import lombok.Data;

@Data
public class ParkingBaiscVO {
    private int bp_seq; // 시퀀스
    private String b_code; // 건물코드
    private String bp_type; // 주차시간타입 h:시간당   ㅡm:분당
    private int pb_time; // 주차시간
    private int pb_price; // 주차요금
    private int pb_free; // 초과되면 금액이 부과되는 시간 ex) 10분 초과시 10   분만 입력
    private int pb_free_price; // 초과되면 금액이 부과되는 금액
    private String reg_id; //  등록자
    private String reg_date; // 등록일
}
