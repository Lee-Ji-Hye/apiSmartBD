package com.restapi.smart.api.persistance;

import java.util.List;

import com.restapi.smart.api.vo.ParkingBDVO;
import com.restapi.smart.api.vo.ParkingTicketVO;

//주차관리 안드로이드 DAO
public interface ParkingAndDAO {

	//주차장 전체 건물 정보
	public List<ParkingBDVO> getBDList();
	
	//하나의 건물정보 가져오기 
	public ParkingBDVO getBDInfo(String b_code);
	
	//하나의 건물 주차권 정보 가져오기
	public List<ParkingTicketVO> getTicketList(String b_code);
}
