package com.restapi.smart.api.persistance;

import com.restapi.smart.api.vo.FoodCartVO;
import com.restapi.smart.api.vo.FoodMenuVO;
import com.restapi.smart.api.vo.FoodOrderInfoVO;
import com.restapi.smart.api.vo.FoodStoreVO;

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
}
