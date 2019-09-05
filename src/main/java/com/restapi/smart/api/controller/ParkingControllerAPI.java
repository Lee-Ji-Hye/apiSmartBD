package com.restapi.smart.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.service.ParkingAndService;
import com.restapi.smart.api.vo.KakaoPayApprovalVO;
import com.restapi.smart.api.vo.ParkingBDVO;
import com.restapi.smart.api.vo.ParkingOrderVO;
import com.restapi.smart.api.vo.ParkingTicketVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController //responsebody 대신
@RequestMapping("api")
public class ParkingControllerAPI {

	private static final Logger log = LoggerFactory.getLogger(ParkingControllerAPI.class);

	@Autowired
	ParkingAndService p_service;
	
	//안드로이드 주차관리 메인레이아웃 지도 마커 정보
	@RequestMapping(value="parking/ParkingInfo")
	public Map<String, Object> ParkingInfo(HttpServletRequest req,Model model) {
		log.info("ParkingInfo()");
		
		
		List<ParkingBDVO> lst = p_service.getBuildingList(req, model);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(lst != null) {
			map.put("parkingDBs", lst);
			map.put("result", "성공");
		}else {
			
			map.put("result", "실패");
		}
		
		return map;
	}
	
	//주차장건물 정보 가져옴
	@RequestMapping(value="parking/ParkingbuidingInfo")
	public Map<String, Object> ParkingbuidingInfo(HttpServletRequest req,Model model) {
		log.info("ParkingbuidingInfo()");
		
		ParkingBDVO vo = p_service.getBuildingInfo(req, model);
		
		List<ParkingBDVO> lst = new ArrayList<ParkingBDVO>();
		lst.add(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(vo != null) {
			map.put("parkingDBs", lst);
			map.put("result", "성공");
		}else {
			
			map.put("result", "실패");
		}
		
		return map;
	}
	
	//안드로이드 주차관리 주차권 정보 가져오기
	@RequestMapping(value="parking/ParkingTicketInfo")
	public Map<String, Object> ParkingTicketInfo(HttpServletRequest req,Model model) {
		log.info("ParkingTicketInfo()");

		List<ParkingTicketVO> lst = p_service.getTicketList(req, model);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(lst != null) {
			map.put("pTickets", lst);
			map.put("result", "성공");
		}else {
			
			map.put("result", "실패");
		}
		
		return map;
	}
	
	//안드로이드 주차관리 한개의 주차권 정보 가져오기
	@RequestMapping(value="parking/ParkingTicketOne")
	public Map<String, Object> ParkingTicketOne(HttpServletRequest req,Model model) {
		log.info("ParkingTicketOne()");

		List<ParkingTicketVO> lst = p_service.getTicketOne(req, model);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(lst != null) {
			map.put("pTickets", lst);
			map.put("result", "성공");
		}else {
			
			map.put("result", "실패");
		}
		
		return map;
	}

	//카카오페이 
	//결제 요청 컨트롤러
	@PostMapping(value = "parking/kakao/ParkingBuyTicket")
	public Map ParkingBuyTicket(@RequestBody ParkingOrderVO vo) {
		log.info("ParkingBuyTicket()");
		System.out.println(vo.getUserid());

		Map result = p_service.PayPakingOrder(vo);
		return result;
	}

	//결제 승인 컨트롤러
	//매핑은 추후 다 post로 바꿀거임
	@PostMapping(value ="kakao/kakaoPaySuccessParking")
	public  KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req) {
		System.out.println("kakaoPaySuccess11111");
		String pg_token = req.getParameter("pg_token"); //결제성공시 넘어오는 토큰값
		System.out.println("토큰~~~ : " + pg_token);
		KakaoPayApprovalVO result = p_service.kakaoPaySuccess(req);
		System.out.println("결과:"+result);

		return result;
	}

	@GetMapping(value ="kakao/kakaoPayCancelParking")
	public String kakaoPayCancel() {
		System.out.println("kakaoPayCancel");
		//주문상세조회 API를 호출하여 상태값이 QUIT_PAYMENT(사용자가 결제를 중단한 상태)인 것을 확인하고 결제 중단 처리를 해야 합니다.
		//주문대기 상태 그대로 둠
		return null;
	}

	@GetMapping(value ="kakao/kakaoPaySuccessFailParking")
	public  String kakaoPaySuccessFail() {
		System.out.println("kakaoPaySuccessFail");
		//결제제한 시간(15분)이 지난 경우 결제준비 API Request로 받은 fail_url 들어옴
		//주문 상태처리상태로 그대로 둠
		return null;
	}
	//카카오페이 끝
	
	/*
	 * //안드로이드 주차관리 관리자 전화번호만 가져오기
	 * 
	 * @ResponseBody// spring에서 안드로이드로 데이터(json)을 보내기 위한 어노테이션
	 * 
	 * @RequestMapping(value="api/parking/ParkingManageHpInfo") public Map<String,
	 * Object> ParkingManageHpInfo(HttpServletRequest req,Model model) {
	 * log.info("ParkingManageHpInfo()");
	 * 
	 * List<ParkingBDVO> lst = p_service.getBuildingList(req, model); Map<String,
	 * Object> map = new HashMap<String, Object>();
	 * 
	 * if(lst != null) { map.put("parkingDBs", lst); map.put("result", "성공"); }else
	 * {
	 * 
	 * map.put("result", "실패"); }
	 * 
	 * return map; }
	 */
	
}