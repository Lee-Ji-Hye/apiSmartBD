package com.restapi.smart.api.service;

import com.restapi.smart.api.vo.FoodMenuVO;
import com.restapi.smart.api.vo.FoodOrderInfoVO;
import com.restapi.smart.api.vo.FoodStoreVO;
import com.restapi.smart.api.vo.KakaoPayApprovalVO;

import javax.servlet.http.HttpServletRequest;
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
    public int beaconCouponChk(HttpServletRequest req);


}
