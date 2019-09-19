package com.restapi.smart.api.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParkingBasicOrderVO {
	private String pay_seq; // 결제 코드
	private String inoutcode; // 입출차코드
	private String userid; // 아이디
	private int pay_price; // 결제 금액
	private String pay_type; //  결제구분 :: TICKET, MONEY
	private int pay_enable_time; // 주차~결제시간
	private String parking_code; //  주차권코드
	private Timestamp pay_day; //  결제시간
	private String tid; // 카카오페이
	private String carnum; //차번호
	private int pb_state; //상태 상태 0:결제대기, 1:결제요청완료 2:결제 완료
}
