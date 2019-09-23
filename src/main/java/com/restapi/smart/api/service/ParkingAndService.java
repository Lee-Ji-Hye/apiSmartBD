package com.restapi.smart.api.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.vo.*;
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

	//한명의 유저 정보 가져오기
	public List<UserCarVO> getUser(HttpServletRequest req, Model model);

	//회원 차량정보 입력
	public void insertUserCar(UserCarVO vo);

	//회원 차량정보 가져오기
	public List<UserCarVO> getUserCar(HttpServletRequest req, Model model);

	//회원 차량정보 삭제
	public void delUserCar(HttpServletRequest req, Model model);

	//입출차 정보 가져오기
	public List<CarHistoryVO> getCarHistory(HttpServletRequest req, Model model);

	//입출차 정보 가져오기 하나만
	public List<CarHistoryVO> getCarHistoryOne(HttpServletRequest req, Model model);

	//주차요금 가졍ㅎ기
	public List<ParkingBaiscVO> getParkingBasic(HttpServletRequest req, Model model);

	//일반 결제 정보 가져오기
	public List<ParkingBasicOrderVO> getParkingOrder(HttpServletRequest req, Model model);

	//카카오페이 일반결제
	public Map PayPakingBasicOrder(ParkingBasicOrderVO vo);

	//카카오페이 일반결제 성공시
	public KakaoPayApprovalVO kakaoPaySuccessBasic(HttpServletRequest req);

	//회원이 보유한 주차권 코드 가져오기
	public  List<ParkingTickeHistoryVO> getUserTickets(HttpServletRequest req, Model model);

	//회원이 보유한 주차권 사용
	public void useTicket(HttpServletRequest req, Model model);

	//회원이 보유한 전체 주차권 정보 가져오기
	public List<ParkingTickeHistoryVO> getUserAllTickets(HttpServletRequest req, Model model);

	//회원 결제 정보 가져오기
	public List<ParkingPaymentVO> getUserPayment(HttpServletRequest req, Model model);
}
