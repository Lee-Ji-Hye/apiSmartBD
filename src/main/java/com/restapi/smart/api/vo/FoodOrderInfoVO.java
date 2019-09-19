package com.restapi.smart.api.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import oracle.sql.TIMESTAMP;

/**
 * 
 * @author jihye
 * 음식 주문 정보 
 */
@Data
public class FoodOrderInfoVO {
	
	private String f_ocode;
	private String comp_seq;         //업체코드
	private String f_status;         //주문대기
    private String f_name;           //주문자명
    private String f_hp;             //핸드폰번호
    private String f_receive_time;  //예상수령시간
    private String f_message;       //요청사항
    private int f_person_num;    //인원
    private String userid;          //아이디
    private String f_serial;        //시리얼번호
    private int f_amount;        //주문 금액
    private int f_sale_price;    //할인 금액(쿠폰)
    private String f_pay_type;      //결제수단
    private String tid;
    private int f_pay_price;     //결제한 금액
    private int f_refund_price;  //환불금액
    private String f_rate;          //수수료
    private String f_regidate;

    private List<FoodCartVO> menulist;

    //그냥.. 업체 이름이랑 업체 전화번호도 같이 가져오자..
    private String comp_org;
    private String comp_hp;

}
