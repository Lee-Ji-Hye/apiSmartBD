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
import sun.rmi.runtime.Log;

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

		if(vo != null) {
			//결제 요청 중 결제대기로 결제 정보를 삽입
			//주차권상품코드,아이디,총결제가격,수량은 안드로이드에서 받아온다

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


		//3. 통신 후 요청이 성공이면 p_status를 '주문대기완료'로 변경, 결제일도 현재시간으로 변경
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_ocode", p_ocode);
		map.put("tid", result.get("tid").toString());
		map.put("p_state", 1); //1:결제요청완료
		map.put("pay_day", new Timestamp(System.currentTimeMillis()));



		p_dao.updatePakingOrder(map);
		return result;
	}
	@Override
	public KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req) {
		//요청 파라미터
		String pg_token = req.getParameter("pg_token");//pg_token=bac6570c5e078b71c589&f_ocode=FD00015
		String p_ocode = req.getParameter("orderCode");


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

	@Override
	public List<UserCarVO> getUser(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		List<UserCarVO> vo = p_dao.getUserInfo(userid);
		return vo;
	}

	//회원 차량정보 입력
	@Override
	public void insertUserCar(UserCarVO vo) {
		p_dao.insertCarinfo(vo);
	}

	@Override
	public List<UserCarVO> getUserCar(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		List<UserCarVO> lst = p_dao.getuserCarInfo(userid);
		return lst;
	}

	@Override
	public void delUserCar(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		p_dao.delUserCarInfo(userid);
	}

	@Override
	public List<CarHistoryVO> getCarHistory(HttpServletRequest req, Model model) {
		String carnum = req.getParameter("carnum");
		List<CarHistoryVO> lst =  p_dao.getCarHistoryList(carnum);
		return lst;
	}

	@Override
	public List<CarHistoryVO> getCarHistoryOne(HttpServletRequest req, Model model) {
		String inoutcode = req.getParameter("inoutcode");
		List<CarHistoryVO> lst =  p_dao.getCarHistoryOne(inoutcode);
		return lst;
	}

	@Override
	public List<ParkingBaiscVO> getParkingBasic(HttpServletRequest req, Model model) {
		String b_code = req.getParameter("b_code");
		List<ParkingBaiscVO> lst =  p_dao.getParkingBasicPrice(b_code);
		return lst;
	}

	@Override
	public List<ParkingBasicOrderVO> getParkingOrder(HttpServletRequest req, Model model) {
		String inoutcode = req.getParameter("inoutcode");
		List<ParkingBasicOrderVO> lst =  p_dao.getParkingOrderList(inoutcode);
		return lst;
	}

	@Override
	public Map PayPakingBasicOrder(ParkingBasicOrderVO vo) {
		// TODO 주문 & 카카오페이 결제요청

		//주문코드(f_ocode) 생성
		String pay_seq = fn.mkUniquecode("pay_seq", "parking_payment_tbl ");

		if(vo != null) {
			//결제 요청 중 결제대기로 결제 정보를 삽입
			//주차권상품코드,아이디,총결제가격,수량은 안드로이드에서 받아온다

			vo.setPay_seq(pay_seq);//주문코드
			vo.setPb_state(0); //주문대기
			vo.setPay_day(new Timestamp(System.currentTimeMillis()));

			p_dao.insertBasicOrder(vo); //결제 대기전 내용 삽입
		}

		//REQUEST PARAM 세팅
		KakaoReadyRequestVO ready_vo = new KakaoReadyRequestVO();
		ready_vo.setPartner_order_id(pay_seq);
		ready_vo.setPartner_user_id(vo.getUserid());
		ready_vo.setItem_name(vo.getCarnum()+"주차요금 결제");
		ready_vo.setQuantity(1);
		ready_vo.setTotal_amount(vo.getPay_price());
		ready_vo.setTax_free_amount(0);
		ready_vo.setApproval_url("http://"+local.getIP_PORT()+"/api/kakao/kakaoPaySuccessParkingBasic");//본인 ip와 port에 맞게 세팅 & 카카오 개발자에도 해당 ip가 등록되어있어야함
		ready_vo.setCancel_url("http://"+local.getIP_PORT()+"/api/kakao/kakaoPayCancelParking");
		ready_vo.setFail_url("http://"+local.getIP_PORT()+"/api/kakao/kakaoPaySuccessFailParking");

		//2. 카카오페이 객체 생성 & 결제요청 통신
		KakaoPayParking kakao = new KakaoPayParking();
		Map<String, Object> result = kakao.payTest(ready_vo);
		result.put("pay_seq", pay_seq);


		//3. 통신 후 요청이 성공이면 p_status를 '주문대기완료'로 변경, 결제일도 현재시간으로 변경
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pay_seq", pay_seq);
		map.put("tid", result.get("tid").toString());
		map.put("pb_state", 1); //1:결제요청완료
		map.put("pay_day", new Timestamp(System.currentTimeMillis()));


		p_dao.updatePakingBasicOrder(map);
		return result;
	}

	@Override
	public KakaoPayApprovalVO kakaoPaySuccessBasic(HttpServletRequest req) {
		String pg_token = req.getParameter("pg_token");//pg_token=bac6570c5e078b71c589&f_ocode=FD00015
		String pay_seq = req.getParameter("orderCode");

		//주문할 정보 가져오기
		ParkingBasicOrderVO order_vo = p_dao.getBasicOrderInfo(pay_seq);

		//order_vo
		KakaoSuccessRequestVO param = new KakaoSuccessRequestVO();
		param.setPartner_order_id(order_vo.getPay_seq()); //결제코드
		param.setPartner_user_id(order_vo.getUserid());
		param.setPg_token(pg_token);
		param.setTid(order_vo.getTid());
		param.setTotal_amount(order_vo.getPay_price());
		param.setCid_secret("");
		param.setPayload("");

		//카카오페이 객체생성 & 결제승인 요청

		KakaoSuccessRequestVO k_vo = new KakaoSuccessRequestVO();
		k_vo.setPartner_order_id(order_vo.getPay_seq());
		k_vo.setPartner_user_id(order_vo.getUserid());
		k_vo.setTotal_amount(order_vo.getPay_price());
		k_vo.setPg_token(pg_token);
		k_vo.setCid_secret("");
		k_vo.setPayload("");
		k_vo.setTid(order_vo.getTid());

		KakaoPayParking kakao = new KakaoPayParking();
		KakaoPayApprovalVO result = kakao.kakaoPaySuccess(pg_token, k_vo);

		//결제승인 떨어지면 주문상태 완료로 변경

		if(result != null && result.getCid() != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pay_seq", pay_seq);
			map.put("pay_day", new Timestamp(System.currentTimeMillis()));
			map.put("pb_state", 2);
			p_dao.updatePakingBasicOrderSucecss(map);
		}
		return result;
	}

	//회원이 보유한 주차권 가져오기
	@Override
	public List<ParkingTickeHistoryVO> getUserTickets(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		String b_code = req.getParameter("b_code");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid",userid);
		map.put("b_code",b_code);
		List<ParkingTickeHistoryVO> lst =  p_dao.getUserTicketList(map);
		return lst;
	}

	@Override
	public void useTicket(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		String p_code = req.getParameter("p_code");
		String count = req.getParameter("count");
		String inoutcode = req.getParameter("inoutcode");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid",userid);
		map.put("p_code",p_code);
		map.put("count",count);
		//주차권 가져오기
		List<ParkingTickeHistoryVO> lst = p_dao.getUserTicketCode(map);

		//차번호 가져오기
		List<CarHistoryVO> vo = p_dao.getCarHistoryOne(inoutcode);
		String carnum =vo.get(0).getCar_number();
		Timestamp now =new Timestamp(System.currentTimeMillis());
		for (int i = 0 ; i<lst.size();i++){
			//사용 내역에 사용 차번호,사용날짜 업데이트
			ParkingTickeHistoryVO usevo = new ParkingTickeHistoryVO();
			usevo.setParking_code(lst.get(i).getParking_code());
			usevo.setCar_number(carnum);
			usevo.setUse_day(now);
			p_dao.updateUseTicket(usevo);

			//결제내역에 넣어주기
			ParkingBasicOrderVO ordervo = new ParkingBasicOrderVO();
			String pay_seq = fn.mkUniquecode("pay_seq", "parking_payment_tbl ");
			ordervo.setPay_seq(pay_seq);
			ordervo.setInoutcode(inoutcode);
			ordervo.setUserid(userid);
			ordervo.setPay_price(0);
			int time =lst.get(i).getHourly();
			if (lst.get(i).getP_type().equalsIgnoreCase("h")){
				time = time*60;
			} else if(lst.get(i).getP_type().equalsIgnoreCase("d")){
				time = time*60*24;
			}
			ordervo.setPay_type("ticket");
			ordervo.setPay_enable_time(time);
			ordervo.setPay_day(now);
			ordervo.setParking_code(lst.get(i).getParking_code());
			ordervo.setPb_state(2);
			System.out.println(ordervo);
			p_dao.insertTicketPay(ordervo);
		}
	}

	//회원이 보유한 전체 주차권 정보 가져오기
	@Override
	public List<ParkingTickeHistoryVO> getUserAllTickets(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		List<ParkingTickeHistoryVO> lst =  p_dao.getUserAllTicketList(userid);
		return lst;
	}

	@Override
	public List<ParkingPaymentVO> getUserPayment(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		List<ParkingPaymentVO> lst =  p_dao.getUserPaymentList(userid);
		return lst;
	}
}
