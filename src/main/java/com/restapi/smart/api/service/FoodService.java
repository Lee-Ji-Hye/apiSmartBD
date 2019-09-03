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

    //카카오페이 결제
    public Map PayFoodeOrder(FoodOrderInfoVO vo);

    public KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req);

}
