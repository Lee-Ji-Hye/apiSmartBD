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
    HashMap<String, Object> res;

    @Autowired
    MemberService m_service;

    @Autowired
    ApiResponseStatus code_status;

    public MemberController() {
        res = new HashMap<String, Object>();
    }

    //회원가입
    @PostMapping(value = "signUp")
    public HashMap<String, Object> signUp(@RequestBody UserVO vo) {
        log.info("회원가입", vo);
        System.out.println(vo);
        //UserVO(userid=너아, userpw=sjsk, name=어아, email=ddjdk@s.d, hp=010-2721-1646, regidate=null, visit=0, visit_date=null)
        int result = m_service.SignUp(vo);
        String msg = code_status.responseMsg(result);

        //if(result == 100) {
        //    res.put("responseCode", 100);
        //    res.put("responseMsg", msg);
        //} else {
            res.put("responseCode", result);
            res.put("responseMsg", msg);
        //}

        return res;
    }

    //로그인
    @PostMapping(value = "signIn")
    public HashMap<String, Object> signIn(@RequestBody UserVO vo) {
        log.info("로그인", "In");
        System.out.println(vo);
        //UserVO(userid=kim, userpw=123, userpw_encode=null, name=null, email=null, hp=null, regidate=null, visit=0, visit_date=null)

        Map<String, Object> result = m_service.SignIn(vo);

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
