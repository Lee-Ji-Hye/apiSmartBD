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
            if(vo.getUserid() == null) vo.setUserid("");
            vo.setF_ocode(f_ocode);
            vo.setF_pay_type("kakao");
            //1. 주문 등록
            //f_status = '주문대기'
            f_dao.WaitOrder(vo);
            f_dao.insertOrderMenus(f_ocode, vo.getMenulist());
        }

        System.out.println("[][][]" + vo);

        List<FoodCartVO> menulist = vo.getMenulist();
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
        ready_vo.setPartner_user_id(vo.getF_name());
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
        String f_ocode = req.getParameter("orderCode");

        //주문할 정보 가져오기
        FoodOrderInfoVO order_vo = f_dao.getOrderInfo(f_ocode);

        //order_vo
        KakaoSuccessRequestVO param = new KakaoSuccessRequestVO();
        param.setPartner_order_id(order_vo.getF_ocode()); //주문코드
        param.setPartner_user_id(order_vo.getF_name());
        param.setPg_token(pg_token);
        param.setTid(order_vo.getTid());
        param.setTotal_amount(order_vo.getF_amount());
        param.setCid_secret("");
        param.setPayload("");


        //카카오페이 객체생성 & 결제승인 요청
        KakaoPay kakao = new KakaoPay();
        KakaoPayApprovalVO result = kakao.kakaoPaySuccess(pg_token, param);

        //결제승인 떨어지면 결제한금액 & 결제일 + 상태 주문접수로 변경
        if(result != null && result.getCid() != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("f_ocode", f_ocode);
            map.put("status", "주문접수");
            map.put("f_pay_price", order_vo.getF_amount());
            f_dao.confirmOrder(map); //f_status = '주문접수'

        }

        return result;
    }

    @Override
    public int kakaoPayRefund(HttpServletRequest req) {
        int result = 569; //초기값 : 주문취소 실패

        String f_ocode = req.getParameter("orderCode").toUpperCase();
        System.out.println("*********" + f_ocode);

        //주문 코드 누락
        if(f_ocode == null) {
            return 561;
        }

        //주문정보 조회
        FoodOrderInfoVO order = f_dao.getOrderInfo(f_ocode);

        if(order != null || order.getTid() != null) {

            //취소 비과세 구함
            double cancel_amount = order.getF_amount();
            double tmp = (double)(cancel_amount / 11.0);
            int cancel_vat = (int)Math.round(tmp); //소숫점 반올림된 비과세 (반올림안하고 버림처리하거나 무조건 올림하면 에러남 --> why? 카카오가 자기네 프로세스에서 취소금액을가지고 비과세 계산한걸 우리가 보낸 비과세랑 비교하기 때문임.)

            //카카오 결체 취소 준비
            KakaoCancleRequestVO cancle_vo = new KakaoCancleRequestVO();
            cancle_vo.setTid(order.getTid());
            cancle_vo.setCancel_amount(order.getF_amount());
            cancle_vo.setCancel_tax_free_amount(0);
            cancle_vo.setCid("");
            cancle_vo.setCid_secret("");
            cancle_vo.setCancel_available_amount(order.getF_amount());
            cancle_vo.setCancel_vat_amount(cancel_vat); //비과세는 반올림
            cancle_vo.setPayload("");

            //카카오 통신 준비
            KakaoPay kakao = new KakaoPay();
            //카카오 통신 결과
            KakaoPayCancleResponseVO cancel_res = kakao.kakaoPayRefund(cancle_vo);

            String kakao_status = cancel_res.getStatus().toUpperCase(); // 대문자 변환
            //[@ kakao_status => SUCCESS_PAYMENT: 결제완료, PART_CANCEL_PAYMENT:부분취소된 상태, CANCEL_PAYMENT:결제된 금액이 모두 취소된 상태, FAIL_PAYMENT:결제 승인 실패]
            if(kakao_status.equals("CANCEL_PAYMENT")) {
               //-> 취소 완료
               int modi = f_dao.modifyOrderStatus(f_ocode, "주문취소");
               if(modi > 0) {
                   result = 560; //주문취소 성공
               }
            } else if(kakao_status.equals("CANCEL_PAYMENT")) {
                //-> 부분취소 완료 (사실 우리는 필요 없음)
            } else if(kakao_status.equals("FAIL_PAYMENT")) {
                //-> 결제 승인 실패(이건 결제요청승인단계에서 실패할때오는거같긴한데.. 확인 필요할듯하지만 일단 여기에서도 한번 걸러보겠음.)
            }

        } else {
            return 502; //카카오 결제정보(TID) 없음
        }

        return result;
    }

    @Override
    public List<FoodOrderInfoVO> getOrderDetail(Map map) {
        List<FoodOrderInfoVO> orderDetail = null;

        String f_ocode = map.get("f_ocode").toString().toUpperCase();
        map.put("f_ocode", f_ocode);

        //주문정보
        FoodOrderInfoVO info = f_dao.getOrderDetailInfo(map);

        System.out.println(map.get("f_ocode"));
        System.out.println(map.get("userid"));
        System.out.println(map.get("username"));
        System.out.println(info + "~~~~~~~~~~~~");

        List<FoodCartVO> menulist = null;

        //주문 메뉴
        if(info == null || info.getF_ocode() == null) {
            return null;
        }

        menulist = f_dao.getOrderMenuList(map);

        if(menulist != null && menulist.size() > 0) {
            info.setMenulist(menulist);
        }

        orderDetail = new ArrayList<FoodOrderInfoVO>();
        orderDetail.add(info);

        return orderDetail;
    }

    @Override
    public int getOrderDetailChk(HttpServletRequest req) {
        int orderCnt = 0;

        String f_ocode = (req.getParameter("f_ocode") == null)? "" : req.getParameter("f_ocode");
        String f_name = (req.getParameter("f_name") == null)? "" : req.getParameter("f_name");

        f_ocode = f_ocode.toUpperCase();

        if(f_ocode.equals("") && f_name.equals("")) {
            return orderCnt; //0
        } else {
            orderCnt = f_dao.getOrderDetailChk(f_ocode, f_name);
        }

        return orderCnt;
    }

    @Override
    public int getModifyStatus(HttpServletRequest req) {

        String f_ocode = req.getParameter("f_ocode");
        String new_status = req.getParameter("new_status");

        int result = f_dao.modifyOrderStatus(f_ocode, new_status);
        return result;
    }

    @Override
    public int beaconCouponChk(HttpServletRequest req) {

        String minor = req.getParameter("minor");
        String major = req.getParameter("major");
        String userid = req.getParameter("userid");

        Map<String, String> map = new HashMap<String, String>();
        map.put("minor", minor);
        map.put("major", major);
        map.put("userid", userid);
        //f_dao.CouponChk(map);

        return 0;
    }


}