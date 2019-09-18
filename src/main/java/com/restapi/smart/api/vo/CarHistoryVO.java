package com.restapi.smart.api.vo;

import com.restapi.smart.api.util.Local;
import lombok.Data;
import oracle.sql.TIMESTAMP;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Data
public class CarHistoryVO {

    private String inoutcode; //입출차코드
    private String car_number; // 차번호
    private String car_number_img; // 차번호이미지
    private String b_code; // 건물코드
    private String in_time; // 입차시간
    private TIMESTAMP parking_time; // 주차시간
    private TIMESTAMP out_time; // 출차시간
    private String parking_location; // 주차위치
    private char parking_state; // 출차전이면 0, 출차후면 1
    private String b_name;//주차장 이름
    private String ip;
    public CarHistoryVO() {
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();// IPv4 아이피 출력.
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /***************************************************
     *     GETTER && SETTER
     *     lombok사용시 getter, setter 별도로 정의 필요 없음
     *     다만, 재정의 하여 사용할 부분이 필요하다면 오버라이딩하여 사용할 것
     ***************************************************/
    public void setCar_number_img(String car_number_img) {
        this.car_number_img = "http://"+ ip+"/smart/resources/parkingimage/"+car_number_img;
    }

}
