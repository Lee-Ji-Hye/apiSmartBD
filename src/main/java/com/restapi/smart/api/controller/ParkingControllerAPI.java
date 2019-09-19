package com.restapi.smart.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.service.ParkingAndService;
import com.restapi.smart.api.vo.*;
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

	//결제 요청 컨트롤러 일반결제
	@PostMapping(value = "parking/kakao/ParkingPayBasic")
	public Map ParkingBuyTicket(@RequestBody ParkingBasicOrderVO vo) {
		log.info("ParkingBuyTicket()");
		System.out.println(vo.getUserid());

		Map result = p_service.PayPakingBasicOrder(vo);
		return result;
	}

	//결제 승인 컨트롤러
	//일반결제
	@PostMapping(value ="kakao/kakaoPaySuccessParkingBasic")
	public  KakaoPayApprovalVO kakaoPaySuccessParkingBasic(HttpServletRequest req) {
		System.out.println("kakaoPaySuccess11111");
		String pg_token = req.getParameter("pg_token"); //결제성공시 넘어오는 토큰값
		System.out.println("토큰~~~ : " + pg_token);
		KakaoPayApprovalVO result = p_service.kakaoPaySuccessBasic(req);
		System.out.println("결과:"+result);

		return result;
	}
	//카카오페이 끝


	@PostMapping(value ="/parking/getUserInfo")
	public Map getUserInfo(HttpServletRequest req,Model model) {
		log.info("getUserInfo()");

		List<UserCarVO> lst = p_service.getUser(req, model);
		UserCarVO vo = lst.get(0);
		Map<String, Object> map = new HashMap<String, Object>();

		if(lst != null) {
			map.put("userid", vo.getUserid());
			map.put("name",vo.getName());
			map.put("hp",vo.getHp());
			map.put("result", "성공");
		}else {

			map.put("result", "실패");
		}

		return map;
	}

	//회원 차번호 정보 입력
	@PostMapping(value ="parking/insertUserCarInfo")
	public Map insertUserCarInfo(@RequestBody UserCarVO vo) {
		log.info("insertUserCarInfo()");

		p_service.insertUserCar(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info","회원 차번호 정보 입력");
		return map;
	}

	//회원 차번호 정보 가져오기
	@PostMapping(value ="parking/getUserCarInfo")
	public Map getUserCarInfo(HttpServletRequest req,Model model) {
		log.info("getUserInfo()");

		List<UserCarVO> lst = p_service.getUserCar(req, model);

		Map<String, Object> map = new HashMap<String, Object>();


		if(lst != null) {
			if(lst.size() >0){
				UserCarVO vo = lst.get(0);
				map.put("userid", vo.getUserid());
				map.put("c_num",vo.getC_num());
				map.put("kind_of_car",vo.getKind_of_car());
			}
			map.put("result", "성공");
		}else {

			map.put("result", "실패");
		}

		return map;
	}

	//회원 차번호 정보 가져오기
	@PostMapping(value ="parking/delUserCarInfo")
	public Map delUserCarInfo(HttpServletRequest req,Model model) {
		log.info("delUserCarInfo()");

		p_service.delUserCar(req, model);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("차량정보 : ","삭제");
		return map;
	}
	//안드로이드 입출차정보
	@PostMapping(value="parking/getCarHistory")
	public Map<String, Object> getCarHistory(HttpServletRequest req,Model model) {
		log.info("getCarHistory()");

		List<CarHistoryVO> lst = p_service.getCarHistory(req, model);
		Map<String, Object> map = new HashMap<String, Object>();

		if(lst != null) {
			map.put("carHistories", lst);
			map.put("result", "성공");
		}else {

			map.put("result", "실패");
		}

		return map;
	}
	//안드로이드 입출차정보
	@PostMapping(value="parking/getCarHistory2")
	public Map<String, Object> getCarHistory2(HttpServletRequest req,Model model) {
		log.info("getCarHistory2()");

		List<CarHistoryVO> lst = p_service.getCarHistoryOne(req, model);
		Map<String, Object> map = new HashMap<String, Object>();

		if(lst != null) {
			map.put("carHistories", lst);
			map.put("result", "성공");
		}else {

			map.put("result", "실패");
		}

		return map;
	}
	//안드로이드 주차요금 불러오기
	@PostMapping(value="parking/getParkingPrice")
	public Map<String, Object> getParkingPrice(HttpServletRequest req,Model model) {
		log.info("getParkingPrice()");

		List<ParkingBaiscVO> lst = p_service.getParkingBasic(req, model);
		Map<String, Object> map = new HashMap<String, Object>();

		if(lst != null) {
			map.put("basicPrices", lst);
			map.put("result", "성공");
		}else {

			map.put("result", "실패");
		}

		return map;
	}
	//안드로이드 결제정보 불러오기
	@PostMapping(value="parking/getPayTime")
	public Map<String, Object> getPayTime(HttpServletRequest req,Model model) {
		log.info("getPayTime()");

		List<ParkingBasicOrderVO> lst = p_service.getParkingOrder(req, model);
		Map<String, Object> map = new HashMap<String, Object>();

		if(lst != null) {
			map.put("orderDetail", lst);
			map.put("result", "성공");
		}else {

			map.put("result", "실패");
		}

		return map;
	}

	//안드로이드 주차권 정보 불러오기
	@PostMapping(value="parking/getUserTickets")
	public Map<String, Object> getUserTickets(HttpServletRequest req,Model model) {
		log.info("getUserTickets()");

		List<ParkingBasicOrderVO> lst = p_service.getParkingOrder(req, model);
		Map<String, Object> map = new HashMap<String, Object>();

		if(lst != null) {
			map.put("orderDetail", lst);
			map.put("result", "성공");
		}else {

			map.put("result", "실패");
		}

		return map;
	}


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
