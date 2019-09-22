package com.restapi.smart.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.vo.RoomBVO;
import com.restapi.smart.api.vo.RoomContractDetailVO;
import com.restapi.smart.api.vo.RoomImageVO;

public interface RoomService {

	//매물 수량
	public List<RoomBVO> getRoomCnt(HttpServletRequest req);

	//매물 정보 리스트
	public List<RoomBVO> getRoomList(HttpServletRequest req);

	//매물 사진
	public List<String> getRoomImage(HttpServletRequest req);

	//계약 정보 등록
	public int insertContract(RoomContractDetailVO vo);

	//계약 정보
	public List<RoomContractDetailVO> getContractList(HttpServletRequest req);
}
