package com.restapi.smart.api.service;

import com.restapi.smart.api.persistance.MemberDAO;
import com.restapi.smart.api.util.ApiResponseStatus;
import com.restapi.smart.api.vo.UserVO;
import com.restapi.smart.security.UserPasswordEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDAO m_dao;

    @Autowired
    ApiResponseStatus code_status;
    /**
     * 회원가입 서비스
     * @param vo
     * @return 300:회원가입성공, 301:파라미터부재,302:아이디부재, 303:비밀번호부재, 304:이메일부재, 305:핸드폰번호부재 306:중복아이디있음, 399:회원가입실패
     */
    @Override
    public int SignUp(UserVO vo) {
        int result = 399; //결과 초기값 : 가입 실패

        if(vo == null) {
            return 301; //파라미터 부재
        }

        String userid = (vo.getUserid() == null)? "" : vo.getUserid(); // null or "" 두가지의 빈값을 ""로ㅗ만 사용하겠다
        String userpw = (vo.getUserpw() == null)? "" : vo.getUserpw();
        String name = (vo.getName() == null)? "" : vo.getName();
        String email = (vo.getEmail() == null)? "" : vo.getEmail();
        String hp = (vo.getHp() == null)? "" : vo.getHp();

        //필수값 체크
        if(userid.equals("")) {
            return 302; //파라미터 부재
        } else if(userpw.equals("")) {
            return 303;
        } else if(email.equals("")) {
            return 304;
        } else if(hp.equals("")) {
            return 305;
        }

        //존재하는 아이디 인지 확인
        int is_id = m_dao.selectUserId(userid);
        if(is_id > 0) {
            return 306; //존재하는 아이디
        }

        //비밀번호 암호화 (이거 웹 bc암호화랑 싱크 맞는지 확인해야함)
        UserPasswordEncode passwordEncode = new UserPasswordEncode();
        String encodePw = passwordEncode.encode(userpw);

        //암호화 비밀번호 세팅
        vo.setUserpw_encode(encodePw);

        //회원가입처리
        int is_join =  m_dao.insertMember(vo);

        //가입 성공
        if(is_join > 0) {
            result = 300;
        }

        return result;
    }

    @Override
    public Map<String, Object> SignIn(UserVO vo) {
        int responseCode = 499; //로그인 실패
        Map<String, Object> map = new HashMap<String, Object>();
        UserVO user = null;

        if(vo == null) {
            map.put("responseCode", 401);//파라미터 부재
            map.put("user", null);
            return map;
        }

        String userid = (vo.getUserid() == null)? "" : vo.getUserid(); // null or "" 두가지의 빈값을 ""로ㅗ만 사용하겠다
        String userpw = (vo.getUserpw() == null)? "" : vo.getUserpw();

        //필수값 체크
        if(userid.equals("")) {
            map.put("responseCode", 402);//아이디 부재
            map.put("user", null);
            return map;
        } else if(userpw.equals("")) {
            map.put("responseCode", 403);//비밀번호 부재
            map.put("user", null);
            return map;
        }

        //비밀번호 불러오기
        String encode_pw = m_dao.selectUserPW(userid);

        if(encode_pw == null || encode_pw.equals("")) {
            map.put("responseCode", 404);//존재하지 않는아이디
            map.put("user", null);
            return map;
        }

        //비밀번호 비교
        //passwordEncoder는 매번 새로운 암호코드를 생성하기 떄문에 비밀번호를 불러와서 비교해야함.
        UserPasswordEncode passwordEncode = new UserPasswordEncode();
        boolean is_match =  passwordEncode.matches(userpw, encode_pw);

        if(!is_match) {
            map.put("responseCode", 405);//비밀번호 불일치
            map.put("user", null);
            return map;
        }

        user = m_dao.getUserInfo(userid);

        //유저정보는 1이나 안드에서 받는 형식에 맞춰야해서 유저정보를 List에 담아 보냄
        //그냥 vo만 보내면 안드 형식에 안맞아서 앱에서 에러남
        List<UserVO> list = new ArrayList<UserVO>();
        list.add(user);

        //로그인 성공!
        map.put("responseCode", 400);
        map.put("user", list);

        return map;
    }

    @Override
    public Map<String, Object> modifyUserInfo(UserVO vo) {
        int responseCode = 0;
        Map<String, Object> map = new HashMap<String, Object>();

        if(vo == null) {
            return null;
        }

        //존재하는 아이디 인지 확인
        String encode_pw = m_dao.selectUserPW(vo.getUserid());

        if(encode_pw == null || encode_pw.equals("")) {
            map.put("responseCode", "404");//존재하지 않는아이디
            map.put("responseMsg", code_status.responseMsg(404));
            return map;
        }

        //비밀번호 비교
        UserPasswordEncode passwordEncode = new UserPasswordEncode();
        boolean is_match =  passwordEncode.matches(vo.getUserpw(), encode_pw);

        if(!is_match) {
            map.put("responseCode", "405");//비밀번호 불일치
            map.put("responseMsg", code_status.responseMsg(405));
            return map;
        }

        int result = m_dao.modifyUserInfo(vo);

        if(result > 0) {
            map.put("responseCode", "200");
            map.put("responseMsg", "수정 성공");
        } else {
            map.put("responseCode", "999");
            map.put("responseMsg", "수정 실패");
        }

        return map;
    }

    @Override
    public Map<String, Object> modifyUserPwd(HashMap<String, String> reqMap) {
        int responseCode = 0;
        Map<String, Object> map = new HashMap<String, Object>();

        if(reqMap == null) {
            return null;
        }

        String userid = reqMap.get("userid");
        String userpw = reqMap.get("userpw");
        String userpw_new = reqMap.get("userpw_new");

        //존재하는 아이디 인지 확인
        String encode_pw = m_dao.selectUserPW(userid);


        if(encode_pw == null || encode_pw.equals("")) {
            map.put("responseCode", "404");//존재하지 않는아이디
            map.put("responseMsg", code_status.responseMsg(404));
            return map;
        }

        //비밀번호 비교
        UserPasswordEncode passwordEncode = new UserPasswordEncode();
        boolean is_match =  passwordEncode.matches(userpw, encode_pw);

        if(!is_match) {
            map.put("responseCode", "405");//비밀번호 불일치
            map.put("responseMsg", code_status.responseMsg(405));
            return map;
        }

        String encodeNewPw = passwordEncode.encode(userpw_new);

        //비밀번호 변경
        int result = m_dao.modifyUserPwd(userid, encodeNewPw);

        if(result > 0) {
            map.put("responseCode", "200");
            map.put("responseMsg", "수정 성공");
        } else {
            map.put("responseCode", "999");
            map.put("responseMsg", "수정 실패");
        }

        return map;
    }

    @Override
    public Map<String, Object> modifyUserWithdraw(HashMap<String, String> reqMap) {
        int responseCode = 0;
        Map<String, Object> map = new HashMap<String, Object>();

        if(reqMap == null) {
            return null;
        }

        String userid = reqMap.get("userid");
        String userpw = reqMap.get("userpw");
        String userpw_new = reqMap.get("userpw_new");

        //존재하는 아이디 인지 확인
        String encode_pw = m_dao.selectUserPW(userid);

        if(encode_pw == null || encode_pw.equals("")) {
            map.put("responseCode", "404");//존재하지 않는아이디
            map.put("responseMsg", code_status.responseMsg(404));
            return map;
        }

        //비밀번호 비교
        UserPasswordEncode passwordEncode = new UserPasswordEncode();
        boolean is_match =  passwordEncode.matches(userpw, encode_pw);

        if(!is_match) {
            map.put("responseCode", "405");//비밀번호 불일치
            map.put("responseMsg", code_status.responseMsg(405));
            return map;
        }

        //관리자인지 확인
        int is_auth = m_dao.userAuthChk(userid);
        if(is_auth > 0) {
            map.put("responseCode", "411");//관리자는 권한 강등 후 탈퇴할 수 있습니다.
            map.put("responseMsg", code_status.responseMsg(411));
            return map;
        }

        //회원 탈퇴
        int result = m_dao.modifyUserWithdraw(userid);
        if(result > 0) {
            map.put("responseCode", "410");
            map.put("responseMsg", code_status.responseMsg(410));
        } else {
            map.put("responseCode", "419");
            map.put("responseMsg", code_status.responseMsg(419));
        }

        return map;
    }

}
