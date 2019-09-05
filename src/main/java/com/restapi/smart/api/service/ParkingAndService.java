package com.restapi.smart.api.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.vo.KakaoPayApprovalVO;
import com.restapi.smart.api.vo.ParkingBDVO;
import com.restapi.smart.api.vo.ParkingOrderVO;
import com.restapi.smart.api.vo.ParkingTicketVO;
import org.springframework.ui.Model;


//주차관리 안드로이드 서비스
public interface ParkingAndService {

	//주차장 전체 건물 리스트 정보 
	public List<ParkingBDVO> getBuildingList(HttpServletRequest req, Model model);
	
	//주차장 건물 정보
	public ParkingBDVO getBuildingInfo(HttpServletRequest req, Model model);
	
	//주차장 주차권 정보
	public List<ParkingTicketVO> getTicketList(HttpServletRequest req, Model model);
	
	//주차장 주차권 정보 한개 상세히
	public List<ParkingTicketVO> getTicketOne(HttpServletRequest req, Model model);
	
	//주차장 관리자 정보 가져오기
	public List<ParkingBDVO> getManagerHpInfo(HttpServletRequest req, Model model);

	//카카오페이 결제 요청
	public Map PayPakingOrder(ParkingOrderVO vo);

	//카카오페이 승인
	public KakaoPayApprovalVO kakaoPaySuccess(HttpServletRequest req);
}
