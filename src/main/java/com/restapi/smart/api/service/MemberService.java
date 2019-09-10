package com.restapi.smart.api.service;

import com.restapi.smart.api.vo.UserVO;

import java.util.Map;

public interface MemberService {

    //회원가입
    public int SignUp(UserVO vo);

    //로그인
    public Map<String, Object> SignIn(UserVO vo);


}
