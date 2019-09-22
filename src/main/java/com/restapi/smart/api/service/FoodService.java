package com.restapi.smart.api.service;

import com.restapi.smart.api.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FoodService {

    //메뉴 리스트
    public List<FoodMenuVO> getMenuList(HttpServletRequest req);

    //업체정보 리스트
    public List<FoodStoreVO> getFoodStoreList(HttpServletRequest req);

    //카카오페이 결제 요청
    public Map PayFoodeOrder(FoodOrderInfoVO vo);

    //카카오페이 결제 승인
    public KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req);

    //카카오페이 결제 취소(환불)
    public int kakaoPayRefund(HttpServletRequest req);

    //주문 정보 조회
    public List<FoodOrderInfoVO> getOrderDetail(Map map);

    //주문 했는지 여부만 체크
    public int getOrderDetailChk(HttpServletRequest req);

    //주문 상태 변경
    public int getModifyStatus(HttpServletRequest req);

    //쿠폰 발급여부 확인
    public HashMap<String, Object>  beaconCouponChk(HttpServletRequest req);

    //주문 리스트 가져오기
    public HashMap<String, Object> getFoodOrderList(HttpServletRequest req);

    //쿠폰정보 가져오기
    public HashMap<String, Object> getCouponList(HttpServletRequest req);



}