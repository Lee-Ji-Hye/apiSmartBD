package com.restapi.smart.api.vo;

import lombok.Data;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Data
public class FoodStoreVO {

	//*FOOD_COMPANY_TBL
	private String f_seq;       //시퀀스
	private String comp_seq;    //업체번호
	private String long_desc;   //상세소개
	private String short_desc;  //한줄소개
	private String f_category;  //카테고리 |* DB에 ENUM걸어놔서 한식, 중식, 일식, 디저트 이외의 카테고리가 들어가면 오류남. 추가하고 싶을경우 알려주세요*|
	private String f_mainimg;   //업체이미지
	private String f_open_stt;  //영업시간(평일) //09:00
	private String f_open_end;  //영업시간(평일) //22:00

	//USER_COMPANY_TBL
	private String comp_org;    //업체명
	private String comp_section;//업체분류
	private String comp_branch; //업체주소
	private String comp_hp;     //업체전화번호

	//FOOD_COUPON_TBL
	private String f_coupon_num; //쿠폰명
	private String f_coupon_name;//쿠폰네임
	private String f_coupon_price;//쿠폰금액

	private String ip = null;

	public FoodStoreVO() {
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();// IPv4 아이피 출력.
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/***************************************************
	 *     GETTER && SETTER
	 *     lombok사용시 getter, setter 별도로 정의 필요 없음
	 *     다만, 재정의 하여 사용할 부분이 필요하다면 오버라이딩하여 사용할 것
	 ***************************************************/
	public void setF_mainimg(String f_mainimg) {
		this.f_mainimg = "http://"+ip+"/smart/resources/images/food/"+f_mainimg;
	}

}