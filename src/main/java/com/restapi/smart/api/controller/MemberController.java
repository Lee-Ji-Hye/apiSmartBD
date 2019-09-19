package com.restapi.smart.api.controller;

import com.restapi.smart.api.service.MemberService;
import com.restapi.smart.api.util.ApiResponseStatus;
import com.restapi.smart.api.vo.UserVO;
import com.restapi.smart.security.UserPasswordEncode;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jihye
 *
 * 어노테이션 설명
 *  -@RestController : @Controller + @ResponseBody
 *  -@RequestMapping : 요청에 대해 어떤 Controller, 어떤 메소드가 처리할지를 맵핑하기 위한 어노테이션
 */
@Slf4j
@RestController
@RequestMapping("api/user")
public class MemberController {
    @Autowired
    MemberService m_service;

    @Autowired
    ApiResponseStatus code_status;

    //회원가입
    @PostMapping(value = "signUp")
    public HashMap<String, String> signUp(@RequestBody UserVO vo) {
        log.info("회원가입");
        int result = m_service.SignUp(vo);
        String msg = code_status.responseMsg(result);

        HashMap<String, String> res = new HashMap<String, String>();
        res.put("responseCode", String.valueOf(result));
        res.put("responseMsg", msg);
        return res;
    }

    //로그인
    @PostMapping(value = "signIn")
    public HashMap<String, Object> signIn(@RequestBody UserVO vo) {
        log.info("로그인", "In");
        System.out.println(vo);
        //UserVO(userid=kim, userpw=123, userpw_encode=null, name=null, email=null, hp=null, regidate=null, visit=0, visit_date=null)

        Map<String, Object> result = m_service.SignIn(vo);

        HashMap<String, Object> res = new HashMap<String, Object>();
        if(result != null && result.get("responseCode") != null) {
            System.out.println("로그인 결과?? : " + result.get("responseCode"));
            String msg = code_status.responseMsg(Integer.parseInt(result.get("responseCode").toString()));
            res.put("responseCode", result.get("responseCode"));
            res.put("responseMsg", msg);
            res.put("user", result.get("user"));
        } else {
            //로그인 대 실패
            System.out.println("로그인 대 실패");
            int responseCode = 499;
            String msg = code_status.responseMsg(responseCode);
            res.put("responseCode", responseCode);
            res.put("responseMsg", msg);
            res.put("user", null);
        }

        return res;
    }

    //정보 수정
    @PostMapping(value = "modifyUserInfo")
    public Map<String, Object> modifyUserInfo(@RequestBody UserVO vo) {
        Map<String, Object> res = m_service.modifyUserInfo(vo);
        return res;
    }

    //비밀번호 변경
    @PostMapping(value = "modifyUserPwd")
    public Map<String, Object> modifyUserPwd(@RequestBody HashMap<String, String> map) {
        Map<String, Object> res = m_service.modifyUserPwd(map);
        return res;
    }

    //회원 탈퇴
    @PostMapping(value = "modifyUserWithdraw")
    public Map<String, Object> modifyUserWithdraw(@RequestBody HashMap<String, String> map) {
        Map<String, Object> res = m_service.modifyUserWithdraw(map);
        return res;
    }


    //비밀번호 암호화 테스트
    @PostMapping(value = "test")
    public String mimi() {
        UserPasswordEncode passwordEncode = new UserPasswordEncode();
        String mimi = passwordEncode.encode("1212");
        //System.out.println("비교: " + inin.matches("1212", inin.encode("1212")));

        System.out.println(mimi);
        return mimi;
    }

}
