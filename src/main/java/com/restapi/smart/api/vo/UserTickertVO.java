package com.restapi.smart.api.vo;

import lombok.Data;
import oracle.sql.TIMESTAMP;
//개인의 주차권 정보 VO
@Data
public class UserTickertVO {
    private String p_code; //주차상품코드
    private String b_code; //건물코드
    private String b_name; //건물이름
    private String parking_code; // 주차권코드 (PT000001)
    private String p_ocode; // 결제 코드  (PK000001)
    private String userid; // 아이디
    private String car_number; // 사용 차번호
    private TIMESTAMP use_day; // 사용날짜

}
