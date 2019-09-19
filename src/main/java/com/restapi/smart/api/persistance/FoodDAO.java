package com.restapi.smart.api.persistance;

import com.restapi.smart.api.vo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jihye
 */
public interface FoodDAO {

    //상품 메뉴 가져오기
    public List<FoodMenuVO> getMenuList(String comp_seq);
    //상품
    public List<FoodStoreVO> getFoodStoreList(Map<String, Object> map);

    //주문 등록 - 상태 '주문대기'
    public int WaitOrder(FoodOrderInfoVO map);

    //주문정보 수정
    public int modifyOrder(Map<String, Object> map);

    //주문 완료 - 상태 '주문완료'
    public int confirmOrder(Map<String, Object> map);

    //주문정보 가져오기
    public FoodOrderInfoVO getOrderInfo(String f_ocode);

    //주문 메뉴 등록
    public int insertOrderMenus(String f_ocode, List<FoodCartVO> menus);

    //주문 상태 변경
    public int modifyOrderStatus(String vo, String new_status);

    //주문 정보 조회
    public FoodOrderInfoVO getOrderDetailInfo(Map map);

    //주문 정보 조회 - 메뉴들
    public List<FoodCartVO> getOrderMenuList(Map map);

    //주문 했는지 안했는지 정보만 숫자로 나타내줌
    public int getOrderDetailChk(String f_ocode, String f_name);

    //public int CouponChk(Map map);

    public List<FoodOrderListVO> getFoodOrderList(String userid);

    // 비콘 정보로 사용 가능한 쿠폰 찾기
    public List<String> getAbleCouponNum(HashMap map);

    //유저에게 쿠폰이 있는지 확인한다.
    public int hasCouponChk(String f_coupon_num, String userid);

    //쿠폰 발급
    public int sendCoupon(String f_coupon_num, String userid);

    //쿠폰 정보
    public List<FoodCouponVO> getCouponCompanyInfo(HashMap map);

    //쿠폰리스트
    public List<FoodCouponVO> getcouponList(String comp_seq, String userid);

    //쿠폰 시리얼 사용
    public int modifySerialUsed(String userid, String serial);

}
