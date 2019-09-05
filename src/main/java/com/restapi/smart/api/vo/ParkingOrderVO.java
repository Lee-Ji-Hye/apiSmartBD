package com.restapi.smart.api.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ParkingOrderVO {
	private String p_ocode;// 결제 코드
	private String p_code;// 주차상품코드
	private String userid; // 아이디
	private int p_state;// 상태
	private int p_oprice;// 총가격
	private Timestamp pay_day;// 결제일
	private int p_count; //구매 수량
	private Timestamp refund_day;// 결제일
	private String tid;
}
