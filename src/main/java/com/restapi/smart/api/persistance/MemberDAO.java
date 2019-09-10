package com.restapi.smart.api.persistance;

import com.restapi.smart.api.vo.UserVO;
import com.restapi.smart.security.domain.Account;

import java.util.List;

//com.restapi.smart.persistance.UserDAO
public interface MemberDAO {

    //존재하는 아이디인지 확인
    public int selectUserId(String userid);
    //비밀번호 불러오기
    public String selectUserPW(String userpw);
    //로그인
    public Account selectMember(String userid);

    //회원가입
    public int insertMember(UserVO vo);

    //회원정보 가져오기
    public UserVO getUserInfo(String vo);



}
