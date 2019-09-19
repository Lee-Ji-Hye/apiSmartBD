package com.restapi.smart.api.vo;

import lombok.Data;

import java.sql.Date;
import java.util.Map;

@Data
public class KakaoPayCancleResponseVO {

    private String aid;       //고유 번호
    private String tid;       //결제 고유 번호
    private String cid;       //가맹점 코드
    private String status;    //결제상태값 (status => SUCCESS_PAYMENT: 결제완료, PART_CANCEL_PAYMENT:부분취소된 상태, CANCEL_PAYMENT:결제된 금액이 모두 취소된 상태.(부분취소 여러 번해서 모두 취소된 경우도 포함), FAIL_PAYMENT:결제 승인 실패)
    private String partner_order_id;                     //가맹점 주문번호
    private String partner_user_id;                      //가맹점 회원 id
    private String payment_method_type;                  //결제 수단
    private Map<String, Object> amount;                  //결제 금액 정보
    private Map<String, Object> canceled_amount;         //이번 요청으로 취소된 금액 정보
    private Map<String, Object> cancel_available_amount; //해당 결제에 대해 취소 가능 금액
    private String item_name;                            //상품 이름.
    private String item_code;                            //상품 코드
    private int quantity;                                //상품 수량
    private Date created_at;                             //결제 준비 요청 시각
    private Date approved_at;                            //결제 승인 시각
    private Date canceled_at;                            //결제 취소 시각
    private String payload;                              //Request로 전달한 값

}
