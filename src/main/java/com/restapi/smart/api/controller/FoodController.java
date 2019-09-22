package com.restapi.smart.api.controller;

import com.restapi.smart.api.service.FoodService;
import com.restapi.smart.api.util.ApiResponseStatus;
import com.restapi.smart.api.util.Local;
import com.restapi.smart.api.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    ApiResponseStatus code_status;


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

    //주문정보 가져오기
    @PostMapping(value = "getOrderDetail")
    public HashMap<String, Object> getOrderDetail(HttpServletRequest req, @RequestBody Map paramMap) {
        System.out.println(paramMap);
        List<FoodOrderInfoVO> orderDetail = f_service.getOrderDetail(paramMap); //주문정보 & 메뉴

        HashMap<String, Object> map = new HashMap<String, Object>();
        if(orderDetail.size() > 0) {
            map.put("orderDetail", orderDetail);
            map.put("responseCode", 100);
            map.put("responseMsg", "성공");
        } else {
            map.put("orderDetail", null);
            map.put("responseCode", 999);
            map.put("responseMsg", "데이터 없음");
        }

        return map;
    }

    @PostMapping(value = "getOrderDetailChk")
    public Map getOrderDetailChk(HttpServletRequest req) {
        int orderCnt = f_service.getOrderDetailChk(req);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("cnt", String.valueOf(orderCnt));
        return map;
    }



    @GetMapping(value = "modifyFoodStatus")
    public HashMap<String, Object>  modifyFoodStatus(HttpServletRequest req) {
        System.out.println("modifyFoodStatus");
        int result = 0;
        String msg = "";
        HashMap<String, Object> map = new HashMap<String, Object>();
        String new_status = req.getParameter("new_status");

        //주문 취소면 환불진행
        if(new_status.equals("주문취소")) {
            result = f_service.kakaoPayRefund(req);  //return responseCode
            msg = code_status.responseMsg(result);//return responseMsg
        }

        map.put("new_status", new_status);
        map.put("payResult", result);
        map.put("responseMsg", msg);
        return map;
    }

    //주문정보 리스트
    @PostMapping(value = "getFoodOrderList")
    public HashMap<String, Object>  getFoodOrderList(HttpServletRequest req) {
        System.out.println("getFoodOrderList");
        HashMap<String, Object> orderlist = f_service.getFoodOrderList(req);  //return responseCode
        System.out.println(orderlist+"~~~~~~~~~~$$");
        return orderlist;
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


    //카카오페이 결제취소
    @GetMapping(value = "kakaoPayRefund")
    public Map<String, Object> kakaoPayRefund(HttpServletRequest req) {
        System.out.println("kakaoPayCancel");

        int result = f_service.kakaoPayRefund(req);  //return responseCode
        String msg = code_status.responseMsg(result);//return responseMsg

        //주문취소로 상태 변경
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("responseCode", result);
        map.put("responseMsg", msg);

        return map;
    }

    @PostMapping(value = "getBeachonCoupon")
    public HashMap<String, Object>  getBeachonCoupon(HttpServletRequest req) {
        System.out.println("getBeachonCoupon : ");
        HashMap<String, Object> res = f_service.beaconCouponChk(req);
        System.out.println(res);
        return res;
    }

    @PostMapping(value = "foodCouponList")
    public HashMap<String, Object>  foodCouponList(HttpServletRequest req) {
        HashMap<String, Object> res = f_service.getCouponList(req);
        System.out.println(res);
        return res;
    }




    @GetMapping(value = "gg")
    public HashMap<String, Object>  gg(HttpServletRequest req) {
        System.out.println("gg ");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", "");
        map.put("major ", "sd");
        map.put("minor", "123");

        HashMap<String, Object> res = f_service.beaconCouponChk(req);

        return res;
    }



    @GetMapping("test")
    public void mimimimi(HttpServletRequest req) {

        String detail = req.getParameter("status");
        System.out.println(detail);
        if(!detail.equals("주문접수") && !detail.equals("주문대기") ) {
            System.out.println("안보이게");
        }
    }

    @GetMapping("testtest2")
    public String zzzzz(HttpServletRequest req) throws ParseException {
        String date_s = " 2011-01-18 00:00:00.0";
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        Date date = dt.parse(date_s);
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy-mm-dd");
        System.out.println(dt1.format(date));

        return String.valueOf(date) + "/ " + dt1.format(date);
    }





}
