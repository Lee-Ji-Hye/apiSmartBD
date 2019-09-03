package com.restapi.smart.persistance;

import com.restapi.smart.security.domain.Account;

//com.restapi.smart.persistance.UserDAO
public interface UserDAO {

    //로그인
    public Account selectMember(String userid);


}
