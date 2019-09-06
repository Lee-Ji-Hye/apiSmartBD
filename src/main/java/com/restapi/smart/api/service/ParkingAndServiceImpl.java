package com.restapi.smart.api.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.pay.KakaoPayParking;
import com.restapi.smart.api.persistance.CodeDAO;
import com.restapi.smart.api.persistance.ParkingAndDAO;
import com.restapi.smart.api.util.Functions;
import com.restapi.smart.api.util.Local;
import com.restapi.smart.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

//주차관리 안드로이드 서비스
@Service
public class ParkingAndServiceImpl implements ParkingAndService{

	@Autowired
	Local local;

	@Autowired
	ParkingAndDAO p_dao;
	
	@Autowired
	CodeDAO c_dao;

	@Autowired
	Functions fn;
	
	//안드로이드 메인페이지 지도 마커 정보 가져오기 ...
	@Override
	public List<ParkingBDVO> getBuildingList(HttpServletRequest req, Model model) {
		List<ParkingBDVO> list = p_dao.getBDList();
		return list;
	}
	
	//지정한 주차장 건물 정보 가져오기 
	@Override
	public ParkingBDVO getBuildingInfo(HttpServletRequest req, Model model) {
		
		String b_code = req.getParameter("b_code");
		ParkingBDVO vo = p_dao.getBDInfo(b_code);
		return  vo;
	}
	
	//주차권 정보 가져오기
	@Override
	public List<ParkingTicketVO> getTicketList(HttpServletRequest req, Model model) {
		
		String b_code = req.getParameter("b_code");
		List<ParkingTicketVO> list = p_dao.getTicketList(b_code);
		return list;
	}

	//하나의 주차권 정보만 가져오기
	@Override
	public List<ParkingTicketVO> getTicketOne(HttpServletRequest req, Model model) {
		String p_code = req.getParameter("p_code");
		List<ParkingTicketVO> list = p_dao.getTicket(p_code);
		return list;
	}
	
	@Override
	public List<ParkingBDVO> getManagerHpInfo(HttpServletRequest req, Model model) {
		String b_code = req.getParameter("b_code");
		List<ParkingTicketVO> list = p_dao.getTicketList(b_code);
		return null;
	}

	//카카오페이결제
	@Override
	public Map PayPakingOrder(ParkingOrderVO vo) {
		// TODO 주문 & 카카오페이 결제요청

		//주문코드(f_ocode) 생성
		String p_ocode = fn.mkUniquecode("p_ocode", "parking_ticket_order_tbl ");
		System.out.println("코드~~~~~~~~~~~"+p_ocode);
		if(vo != null) {
			//결제 요청 중 결제대기로 결제 정보를 삽입
			//주차권상품코드,아이디,총결제가격,수량은 안드로이드에서 받아온다
			System.out.println("아이디:"+vo.getUserid());
			vo.setP_ocode(p_ocode);//주문코드
			vo.setP_state(0);//상태 0: 결제 대기
			vo.setPay_day(new Timestamp(System.currentTimeMillis()));

			p_dao.insertTicketOrder(vo); //결제 대기전 내용 삽입
		}
		
		//REQUEST PARAM 세팅
		KakaoReadyRequestVO ready_vo = new KakaoReadyRequestVO();
		ready_vo.setPartner_order_id(p_ocode);
		ready_vo.setPartner_user_id(vo.getUserid());
		ready_vo.setItem_name("smart 주차권");
		ready_vo.setQuantity(1);
		ready_vo.setTotal_amount(vo.getP_oprice());
		ready_vo.setTax_free_amount(0);
		ready_vo.setApproval_url("http://"+local.getIP_PORT()+"/api/kakao/kakaoPaySuccessParking");//본인 ip와 port에 맞게 세팅 & 카카오 개발자에도 해당 ip가 등록되어있어야함
		ready_vo.setCancel_url("http://"+local.getIP_PORT()+"/api/kakao/kakaoPayCancelParking");
		ready_vo.setFail_url("http://"+local.getIP_PORT()+"/api/kakao/kakaoPaySuccessFailParking");
		
		//2. 카카오페이 객체 생성 & 결제요청 통신
		KakaoPayParking kakao = new KakaoPayParking();
		Map<String, Object> result = kakao.payTest(ready_vo);
		result.put("p_ocode", p_ocode);
		System.out.println("코드22222222222222:"+p_ocode);

		//3. 통신 후 요청이 성공이면 p_status를 '주문대기완료'로 변경, 결제일도 현재시간으로 변경
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_ocode", p_ocode);
		map.put("tid", result.get("tid").toString());
		map.put("p_state", 1); //1:결제요청완료
		map.put("pay_day", new Timestamp(System.currentTimeMillis()));

		System.out.println("코드33333333:"+map);

		p_dao.updatePakingOrder(map);
		return result;
	}
	@Override
	public KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req) {
		//요청 파라미터
		String pg_token = req.getParameter("pg_token");//pg_token=bac6570c5e078b71c589&f_ocode=FD00015 
		String p_ocode = req.getParameter("orderCode");
		System.out.println("주문정보코드:"+p_ocode);

		//주문할 정보 가져오기
		ParkingOrderVO order_vo = p_dao.getOrderInfo(p_ocode);

		//order_vo
		KakaoSuccessRequestVO param = new KakaoSuccessRequestVO();
		param.setPartner_order_id(order_vo.getP_ocode()); //주문코드
		param.setPartner_user_id(order_vo.getUserid());
		param.setPg_token(pg_token);
		param.setTid(order_vo.getTid());
		param.setTotal_amount(order_vo.getP_oprice());
		param.setCid_secret("");
		param.setPayload("");

		//카카오페이 객체생성 & 결제승인 요청

		KakaoSuccessRequestVO k_vo = new KakaoSuccessRequestVO();
		k_vo.setPartner_order_id(order_vo.getP_ocode());
		k_vo.setPartner_user_id(order_vo.getUserid());
		k_vo.setTotal_amount(order_vo.getP_oprice());
		k_vo.setPg_token(pg_token);
		k_vo.setCid_secret("");
		k_vo.setPayload("");
		k_vo.setTid(order_vo.getTid());

		KakaoPayParking kakao = new KakaoPayParking();
		KakaoPayApprovalVO result = kakao.kakaoPaySuccess(pg_token, k_vo);
		
		//결제승인 떨어지면 주문상태 완료로 변경

		 if(result != null && result.getCid() != null) {
		  	Map<String, Object> map = new HashMap<String, Object>();
		  	map.put("p_ocode", p_ocode);
			 map.put("pay_day", new Timestamp(System.currentTimeMillis()));
		  	map.put("p_state", 2);
		  	p_dao.updatePakingOrderSucecss(map);

		  	//그뒤 구입한 수량 만큼 주차권 발급
             //주문코드(f_ocode) 생성
             for (int i=0;i<order_vo.getP_count();i++ ) {
                 String parking_code = fn.mkUniquecode("parking_code", "parking_ticket_history_tbl ");
                 ParkingTickeHistoryVO pth_vo = new ParkingTickeHistoryVO();
                 pth_vo.setUserid(order_vo.getUserid());
                 pth_vo.setParking_code(parking_code);
                 pth_vo.setP_ocode(order_vo.getP_ocode());

                 p_dao.insertTicketHistory(pth_vo);
             }
		  }
		return result;
		//return null;
	}
}