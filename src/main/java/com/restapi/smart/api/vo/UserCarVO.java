package com.restapi.smart.api.vo;

import lombok.Data;

@Data
public class UserCarVO {
    private String userid; //회원 아이디
    private String name; //이름
    private String hp; //전화번호

    private String c_num; //차번호
    private String kind_of_car; //차종



}
