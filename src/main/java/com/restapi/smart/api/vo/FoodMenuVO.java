package com.restapi.smart.api.vo;


import java.sql.Timestamp;

import com.restapi.smart.api.util.Local;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class FoodMenuVO {

	public FoodMenuVO() {
		Local local = new Local();
		this.ip = local.getIP();
	}
	//*FOOD_MENU_TBL
	private String f_code;       //메뉴코드
	private String comp_seq;    //업체코드
	private String f_catagory;    //카테고리
	private String f_name;       //메뉴명
	private int f_price;       //메뉴가격
	private String f_takeout;    //테이크아웃 유무(Y,N)
	private int f_takeout_sale;  //테이크아웃 할인가격
	private String f_img;       //이미지
	private String userid;       //등록자 아이디
	private String f_icon;       //메뉴아이콘(NEW, HOT, BEST 등)
	private Timestamp f_regidate;//등록일

	//*USER_COMP_TBL
	private String comp_org;     //업체명  |*DB없음 조인해서 가져옴*|

	private String ip = null;


	/***************************************************
	 *     GETTER && SETTER
	 *     lombok사용시 getter, setter 별도로 정의 필요 없음
	 *     다만, 재정의 하여 사용할 부분이 필요하다면 오버라이딩하여 사용할 것
	 ***************************************************/

	public void setF_img(String f_img) {
		this.f_img = "http://"+ip+"/smart/resources/images/food/"+f_img;
	}

}