package com.restapi.smart.api.vo;

import lombok.Data;
import oracle.sql.TIMESTAMP;

@Data
public class ParkingTickeHistoryVO {
    private String parking_code; //주차권코드
    private String userid; // 아이디
    private String p_ocode;// 결제 코드
    private String car_number; // 사용 차번호
    private TIMESTAMP use_day; // 사용날짜

}
