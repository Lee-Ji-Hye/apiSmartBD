package com.restapi.smart.api.controller;

import com.restapi.smart.api.service.FoodService;
import com.restapi.smart.api.util.Local;
import com.restapi.smart.api.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("api/food")
public class FoodController {
    @Autowired
    FoodService f_service;



    @GetMapping("foods")
    public String foodTesr() {

        return "정상동작chk";
    }

    // 앱 > 음식 상세보기 (메뉴, 업체정보 return)
    @GetMapping(value="getFoodMenuList")
    public HashMap<String, Object> getFoodDetailInfo(HttpServletRequest req) {

        List<FoodMenuVO> menuRes = f_service.getMenuList(req);//메뉴정보
        HashMap<String, Object> map = new HashMap<String, Object>();

        if(menuRes != null) {
            map.put("menus", menuRes);
            map.put("responseCode", 100);
            map.put("responseMsg", "성공");
        } else {
            map.put("menus", null);
            map.put("responseCode", 999);
            map.put("responseMsg", "데이터 없음");
        }

        return map;
    }

    //업체정보 가져오기
    @GetMapping(value="getStoreList")
    public HashMap<String, Object> getStoreList(HttpServletRequest req) {

        List<FoodStoreVO> result = f_service.getFoodStoreList(req);

        HashMap<String, Object> map = new HashMap<String, Object>();
        if(result.size() > 0) {
            map.put("stores", result);
            map.put("responseCode", 100);
            map.put("responseMsg", "성공");
        } else {
            map.put("stores", null);
            map.put("responseCode", 999);
            map.put("responseMsg", "데이터 없음");
        }
        return map;
    }

    /*
        카카오페이
     */
    //결제 요청 컨트롤러
    @PostMapping(value = "payTest")
    public Map orderTest(@RequestBody FoodOrderInfoVO vo) {

        Map result = f_service.PayFoodeOrder(vo);
        log.info("payTest", result);
        return result;
    }


    //결제 승인 컨트롤러
    //매핑은 추후 다 post로 바꿀거임
    @PostMapping(value = "kakaoPaySuccess")
    public KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req) {
        //System.out.println("kakaoPaySuccess");
        String pg_token = req.getParameter("pg_token"); //결제성공시 넘어오는 토큰값
        KakaoPayApprovalVO result = f_service.kakaoPaySuccess(req);
        //System.out.println("토큰~~~ : " + pg_token);
        //System.out.println(result);

        return result;
    }

    @PostMapping(value = "kakaoPayCancel")
    public String kakaoPayCancel() {
        System.out.println("kakaoPayCancel");
        //주문상세조회 API를 호출하여 상태값이 QUIT_PAYMENT(사용자가 결제를 중단한 상태)인 것을 확인하고 결제 중단 처리를 해야 합니다.
        //주문대기 상태 그대로 둠
        return null;
    }


}
