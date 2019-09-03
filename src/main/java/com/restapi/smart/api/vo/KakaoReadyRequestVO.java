package com.restapi.smart.api.vo;

import lombok.Data;

/**
 * 
 * @author jihye
 * 카카오페이 결제요청
 * (아래 프로퍼티는 모두 필수값임)
 *
 */
@Data
public class KakaoReadyRequestVO {
	
	private String cid;              //가맹점 코드(10자)
	private String partner_order_id; //가맹점 주문번호
	private String partner_user_id;  //가맹점 회원 id
	private String item_name;        //상품명. 최대 100자
	private int quantity;            //상품 수량
	private int total_amount;        //상품 총액
	private int tax_free_amount;     //상품 비과세 금액
	private String approval_url;     //결제 성공시 redirect url 최대 255자
	private String cancel_url;       //결제 취소시 redirect url 최대 255자
	private String fail_url;         //결제 실패시 redirect url 최대 255자
	
}
