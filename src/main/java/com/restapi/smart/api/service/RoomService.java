package com.restapi.smart.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.vo.RoomBVO;

public interface RoomService {
	
	//매물 수량
	public List<RoomBVO> getRoomCnt(HttpServletRequest req);
	
	//매물 정보 리스트
	public List<RoomBVO> getRoomList(HttpServletRequest req);
}
