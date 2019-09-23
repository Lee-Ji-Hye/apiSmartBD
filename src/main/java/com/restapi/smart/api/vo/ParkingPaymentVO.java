package com.restapi.smart.api.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParkingPaymentVO {
    private String userid ; //-아이디
    private int pay_price; //결제 금액
    private String pay_type; //결제구분 :: TICKET, MONEY
    private int pay_enable_time; //주차~결제시간
    private String parking_code; //주차권코드
    private Timestamp pay_day; //결제시간
    private String car_number; // 사용 차번호

    private String b_name; //건물 이름
    private String p_code; //주차상품코드
    private int count; //주차권갯수
    private String p_type;//주차권 타입

}
