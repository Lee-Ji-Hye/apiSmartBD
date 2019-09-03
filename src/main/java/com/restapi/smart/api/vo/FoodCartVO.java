package com.restapi.smart.api.vo;

import lombok.Data;

/**
 * 
 * @author jihye
 * 장바구니 메뉴
 * (사실 업체코드, 업체명, 카테고리는 필요없음.. 나중에 시간나면 정리하겠음)
 *
 */
@Data
public class FoodCartVO {

	private String comp_seq;    //업체코드
    private String comp_org;    //업체명
    private String f_code;      //메뉴코드
    private String f_catagory;  //카테고리
    private String f_name;      //메뉴명
    private String f_price;     //메뉴가격
    private int f_cnt;          //수량
    
}
