package com.restapi.smart.api.service;

import com.restapi.smart.api.pay.KakaoPay;
import com.restapi.smart.api.persistance.CodeDAO;
import com.restapi.smart.api.persistance.FoodDAO;
import com.restapi.smart.api.util.Functions;
import com.restapi.smart.api.util.Local;
import com.restapi.smart.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodDAO f_dao;
    @Autowired
    CodeDAO c_dao;

    @Autowired
    Functions fn;

    @Autowired
    Local local; // IP, PORT 접속 정보

    @Override
    public List<FoodMenuVO> getMenuList(HttpServletRequest req) {
        // TODO 메뉴 리스트 가져오기 테스트

        String comp_seq = (req.getParameter("comp_seq") == "")? null : req.getParameter("comp_seq");
        List<FoodMenuVO> menuList = f_dao.getMenuList(comp_seq);
        if(menuList.size() <= 0) {
            System.out.println("뎃타 없음");
        } else {
            System.out.println(menuList.get(0).toString());
        }
        return menuList;
    }

    @Override
    public List<FoodStoreVO> getFoodStoreList(HttpServletRequest req) {
        // TODO 업체정보 가져오기
        String f_category = (req.getParameter("f_category") == "")? null : req.getParameter("f_category");
        String comp_seq = (req.getParameter("comp_seq") == "")? null : req.getParameter("comp_seq");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("f_category", f_category);
        map.put("comp_seq", comp_seq);
        System.out.println(map);
        List<FoodStoreVO> storeList = f_dao.getFoodStoreList(map);

        if(storeList.size() <= 0) {
            System.out.println("뎃타 없음");
        } else {
            System.out.println(storeList.get(0).toString());
        }

        return storeList;
    }


    @Override
    public Map PayFoodeOrder(FoodOrderInfoVO vo) {
        // TODO 주문 & 카카오페이 결제요청

        //주문코드(f_ocode) 생성
        String f_ocode = fn.mkUniquecode("f_ocode", "food_order_tbl");

        if(vo != null && vo.getMenulist() != null) {
            if(vo.getUserid() == null) vo.setUserid("guest");
            vo.setF_ocode(f_ocode);
            vo.setF_pay_type("kakao");
            //1. 주문 등록
            //f_status = '주문대기'
            f_dao.WaitOrder(vo);
            f_dao.insertOrderMenus(f_ocode, vo.getMenulist());
        }

        System.out.println("[][][]" + vo);

        ArrayList<FoodCartVO> menulist = vo.getMenulist();
        int sumCnt = 0; //주문메뉴 수량 합
        String payTitle = null;
        if(menulist != null) {
            for(FoodCartVO menu : menulist) {
                sumCnt += menu.getF_cnt();
                if(payTitle == null) {
                    payTitle = menu.getF_name();
                }
            }
            payTitle = payTitle + " 외"+ (sumCnt - 1);
        }

        //REQUEST PARAM 세팅
        KakaoReadyRequestVO ready_vo = new KakaoReadyRequestVO();
        ready_vo.setPartner_order_id(f_ocode);
        ready_vo.setPartner_user_id("system");
        ready_vo.setItem_name(payTitle);
        ready_vo.setQuantity(sumCnt);
        ready_vo.setTotal_amount(vo.getF_amount());
        ready_vo.setTax_free_amount(0);
        ready_vo.setApproval_url("http://"+local.getIP_PORT()+"/api/food/kakaoPaySuccess");//본인 ip와 port에 맞게 세팅 & 카카오 개발자에도 해당 ip가 등록되어있어야함
        ready_vo.setCancel_url("http://"+local.getIP_PORT()+"/api/food/kakaoPayCancel");
        ready_vo.setFail_url("http://"+local.getIP_PORT()+"/api/food/kakaoPaySuccessFail");

        //2. 카카오페이 객체 생성 & 결제요청 통신
        KakaoPay kakao = new KakaoPay();
        Map<String, Object> result = kakao.payTest(ready_vo);
        result.put("f_ocode", f_ocode);

        //3. 통신 후 요청이 성공이면 f_status를 '주문완료'로 변경
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("f_ocode", f_ocode);
        map.put("tid", result.get("tid").toString());

        f_dao.modifyOrder(map); //f_status = '주문완료'

        return result;
    }

    @Override
    public KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req) {
        //요청 파라미터
        String pg_token = req.getParameter("pg_token");//pg_token=bac6570c5e078b71c589&f_ocode=FD00015
        String f_ocode = req.getParameter("f_ocode");

        //주문할 정보 가져오기
        FoodOrderInfoVO order_vo = f_dao.getOrderInfo(f_ocode);

        //order_vo
        KakaoSuccessRequestVO param = new KakaoSuccessRequestVO();
        param.setPartner_order_id(order_vo.getF_ocode()); //주문코드
        param.setPartner_user_id("system");
        param.setPg_token(pg_token);
        param.setTid(order_vo.getTid());
        param.setTotal_amount(order_vo.getF_amount());
        param.setCid_secret("");
        param.setPayload("");


        //카카오페이 객체생성 & 결제승인 요청
        KakaoPay kakao = new KakaoPay();
        KakaoPayApprovalVO result = kakao.kakaoPaySuccess(pg_token, param);

        //결제승인 떨어지면 결제한금액 & 결제일 + 주문상태 완료로 변경
        if(result != null && result.getCid() != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("f_ocode", f_ocode);
            map.put("status", "주문완료");
            map.put("f_pay_price", order_vo.getF_amount());
            f_dao.confirmOrder(map); //f_status = '주문완료'

        }

        return result;
    }




}
