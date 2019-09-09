package com.restapi.smart.api.vo;

import lombok.Data;

@Data
public class UserVO {
    private String userid;
    private String userpw;
    private String userpw_encode; //암호화된 비밀번호
    private String name;
    private String email;
    private String hp;
    private String regidate;
    private int visit;
    private String visit_date;

}
