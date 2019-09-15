package com.restapi.smart.api.persistance;

import java.util.List;
import java.util.Map;

import com.restapi.smart.api.vo.*;

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
}
