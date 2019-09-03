package com.restapi.smart.api.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class KakaoPayApprovalVO {
	private String aid;
	private String tid;
	private String cid;
	private String sid;
	private String partner_order_id;
	private String partner_user_id;
	private String payment_method_type;
	//amount	결제 금액 정보	JSON Object
	//card_info	결제 상세 정보(결제수단이 카드일 경우만 포함)	JSON Object
	private String item_name;
	private String item_code;
	private int quantity;
	private Date created_at;
	private Date approved_at;
	private String payload;
	
}
