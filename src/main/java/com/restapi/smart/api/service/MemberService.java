package com.restapi.smart.api.service;

import com.restapi.smart.api.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface MemberService {

    //회원가입
    public int SignUp(UserVO vo);

    //로그인
    public Map<String, Object> SignIn(UserVO vo);

    //유저 정보 수정
    public Map<String, Object> modifyUserInfo(UserVO vo);

    //유저 비밀번호 변경
    public Map<String, Object> modifyUserPwd(HashMap<String, String> map);

    //회원 탈퇴
    public Map<String, Object> modifyUserWithdraw(HashMap<String, String> map);

}
