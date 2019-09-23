package com.restapi.smart.api.persistance;


import com.restapi.smart.api.vo.*;

import java.util.List;
import java.util.Map;



//주차관리 안드로이드 DAO
public interface ParkingAndDAO {

	//주차장 전체 건물 정보
	public List<ParkingBDVO> getBDList();

	//하나의 건물정보 가져오기
	public ParkingBDVO getBDInfo(String b_code);

	//하나의 건물 주차권 정보 가져오기
	public List<ParkingTicketVO> getTicketList(String b_code);


	//하나의 건물 하나의 주차권 정보 가져오기
	public List<ParkingTicketVO> getTicket(String p_code);

	//주차권 결제 정보 넣기
	public void insertTicketOrder(ParkingOrderVO vo);

	//주차권 주문 정보 변경(상태,결제일)
	public void updatePakingOrder(Map map);

	//결제코드로 주문정보 가져오기
	public ParkingOrderVO getOrderInfo(String p_ocode);

	//결제승인 이후 주문정보 업데이트
	public void updatePakingOrderSucecss(Map map);

	//주차권 발급
	public void insertTicketHistory(ParkingTickeHistoryVO pth_vo);

	//주차장 회원 정보 가져오기
	public List<UserCarVO> getUserInfo(String userid);

	//주차장 회원차량 정보 삽입
	public void insertCarinfo(UserCarVO vo);

	//주차장 회원차량정보 가져오기
	public List<UserCarVO> getuserCarInfo(String userid);

	//주차장 회원차량정보 삭제
	public void delUserCarInfo(String userid);

	//주차장 입출차 정보 가져오기
	public List<CarHistoryVO> getCarHistoryList(String carnum);

	//주차장 입출차 정보 하나만 가져오기
	public List<CarHistoryVO> getCarHistoryOne(String inoutcode);

	//주차요금
	public List<ParkingBaiscVO> getParkingBasicPrice(String b_code);

	//결제 정보 가져오기
	public List<ParkingBasicOrderVO> getParkingOrderList(String inoutcode);

	//주차요금 일반 결제 정보 삽입
	public void insertBasicOrder(ParkingBasicOrderVO vo);
	//주차요금 일반 결제 결제대기완료시 업데이트
	public void updatePakingBasicOrder(Map<String, Object> map);

	//주차일반결제 정보 가져오기
	public ParkingBasicOrderVO getBasicOrderInfo(String pay_seq);

	//주차 일반 결제 성공시 업데이트
	public void updatePakingBasicOrderSucecss(Map<String, Object> map);

	//회원이 보유한 주차권 리스트
	public List<ParkingTickeHistoryVO> getUserTicketList(Map map);

	//회원이 보유한 주차권 코드 리스트
	public List<ParkingTickeHistoryVO> getUserTicketCode(Map<String, Object> map);

	//사용한 주차권 사용리스트 업데이트
	public void updateUseTicket(ParkingTickeHistoryVO usevo);

	//사용한 주차권  결제내역에 넣기
	public void insertTicketPay(ParkingBasicOrderVO ordervo);

	//회원이 보유한 전체 주차권 코드 리스트
	public List<ParkingTickeHistoryVO> getUserAllTicketList(String userid);

	//회원 결제 내역
	public List<ParkingPaymentVO> getUserPaymentList(String userid);
}
