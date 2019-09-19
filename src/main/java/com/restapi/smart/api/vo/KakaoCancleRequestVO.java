package com.restapi.smart.api.vo;

import lombok.Data;

@Data
public class KakaoCancleRequestVO {

    private String cid;
    private String cid_secret;          //필수x
    private String tid;
    private int cancel_amount;          //취소 금액	O
    private int cancel_tax_free_amount; //	취소 비과세 금액	O	Integer
    private int cancel_vat_amount;      //	취소 부가세 금액(안보낼 경우 (취소 금액 - 취소 비과세 금액)/11 : 소숫점이하 반올림)	X	Integer
    private int cancel_available_amount;//	취소가능금액(결제취소 요청 금액 포함)	X	Integer
    private String payload;//	해당 Request와 매핑해서 저장하고 싶은 값. 최대 200자	X	String

}
