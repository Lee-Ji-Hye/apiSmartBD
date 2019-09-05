package com.restapi.smart.api.vo;

import lombok.Data;

@Data
public class KakaoSuccessRequestVO {

    private String  cid_secret;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;
    private String payload;
    private int total_amount;

}
