package com.restapi.smart.api.vo;

import lombok.Data;

@Data
public class ParkingBDVO {
	
	private String b_code; // 건물코드
	private String b_name; // 건물명
	private int p_floor; //층수
	private int able_position; // 총 주차자리
	private int able_height; // 출차높이제한(m)
	private double lat;	//위도
	private double lon; //경도
	private String operate_time_day; //평일영업시간        
	private String operate_time_week; //공휴일영업시간
	private String operate_tel; //주차관리사무소번호
	
	//건물 주소
	private String b_area1; //주소1 ex)서울시
	private String b_area2; //주소2 ex)금천구
	private String b_address; //상세 주소 ex) 가산동 654-5
	
	//건물 상세
	private int p_seq; // 상세정보 시퀀스
	private String p_detail_floor; //층 (B1, B2,,)
	private int able_position_floor; // 층별 주차가능자리수
	private int disable_position; // 장애인구역
	private int reserved_position; //-- 지정주차구역
	private String ask; //-- 기타사항
	

}
