package com.restapi.smart.api.vo;

import java.sql.Timestamp;

import lombok.Data;
@Data
public class ParkingTicketVO {
	private String p_code; //주차상품코드
    private String b_code; //건물코드
    
    private char p_type; // 주차시간타입 h:시간당   ㅡm:분당 
    private int hourly; // 0이하는 분 단위 , 0.1: 10분, 0.5:30분, 1:1시간, ... , 24:1일
    private int price; //가격
    private String reg_id; //등록자
    private Timestamp reg_date; //등록일
	private String update_id; //수정자 (안써도 무방하나 수정시 최종 수정자를 남기려면 사용하세요)
	private Timestamp update_date; //수정일 (안써도 무방하나 수정시 최종 수정자를 남기려면 사용하세요)

	//건물 주소
	private String b_name; //건물이름
	private String b_area1; //주소1 ex)서울시
	private String b_area2; //주소2 ex)금천구
	private String b_address; //상세 주소 ex) 가산동 654-5
}