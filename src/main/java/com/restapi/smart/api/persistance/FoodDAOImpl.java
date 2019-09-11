package com.restapi.smart.api.persistance;

import com.restapi.smart.api.vo.FoodCartVO;
import com.restapi.smart.api.vo.FoodMenuVO;
import com.restapi.smart.api.vo.FoodOrderInfoVO;
import com.restapi.smart.api.vo.FoodStoreVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FoodDAOImpl implements FoodDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public List<FoodMenuVO> getMenuList(String comp_seq) {
        // TODO 메뉴 가져오기
        return sqlSession.selectList("FoodDAO.getMenuList", comp_seq);
    }

    @Override
    public List<FoodStoreVO> getFoodStoreList(Map<String, Object> map) {
        // TODO 스토어 정보 가져오기
        return sqlSession.selectList("FoodDAO.getFoodStoreList", map);
    }

    @Override
    public int WaitOrder(FoodOrderInfoVO vo) {
        // TODO 주문 정보 등록
        return sqlSession.insert("FoodDAO.insertWaitOrderInfo", vo);
    }

    @Override
    public FoodOrderInfoVO getOrderInfo(String f_ocode) {
        // TODO 주문 정보
        System.out.println("~~~~" + f_ocode);
        return sqlSession.selectOne("FoodDAO.getOrderInfo", f_ocode);
    }

    @Override
    public int insertOrderMenus(String f_ocode, List<FoodCartVO> menus) {
        // TODO 주문 메뉴 등록
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("f_ocode", f_ocode);
        map.put("list", menus);
        return sqlSession.insert("FoodDAO.insertOrderMenus", map);
    }

    @Override
    public int modifyOrderStatus(String f_ocode, String new_status) {
        Map<String, Object> map =  new HashMap<String, Object>();
        map.put("f_ocode", f_ocode);
        map.put("new_status", new_status);

        return sqlSession.update("FoodDAO.modifyOrderStatus", map);
    }

    @Override
    public int modifyOrder(Map<String, Object> map) {
        // TODO 결제요청 후 tid 업뎃
        return sqlSession.update("FoodDAO.modifyOrderInfo", map);
    }

    @Override
    public int confirmOrder(Map<String, Object> map) {
        // TODO 주문 완료
        //결제한금액, 결제일 등 추가 세팅
        return sqlSession.update("FoodDAO.comfirmOrderInfo", map);
    }

    @Override
    public FoodOrderInfoVO getOrderDetailInfo(Map map) {
        return sqlSession.selectOne("FoodDAO.getOrderDetailInfo", map);
    }

    @Override
    public List<FoodCartVO> getOrderMenuList(Map map) {
        return sqlSession.selectList("FoodDAO.getOrderMenuList", map);
    }

    @Override
    public int getOrderDetailChk(String f_ocode, String f_name) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("f_ocode", f_ocode);
        map.put("f_name", f_name);
        return sqlSession.selectOne("FoodDAO.getOrderDetailChk", map);
    }

//    @Override
//    public int CouponChk(Map map) {
//        return sqlSession.selectOne("FoodDAO.CouponChk", map);
//    }

}
