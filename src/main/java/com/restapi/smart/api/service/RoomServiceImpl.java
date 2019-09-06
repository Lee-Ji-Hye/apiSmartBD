package com.restapi.smart.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.persistance.RoomDAO;
import com.restapi.smart.api.vo.RoomBVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	RoomDAO r_dao;
	
	@Override
	public List<RoomBVO> getRoomCnt(HttpServletRequest req) {
		// TODO 매물 수량 가져오기

		List<RoomBVO> roomCnt = r_dao.getRoomCnt();
		
		if(roomCnt.size() <= 0) {
			System.out.println("데이터 없음");
		} else {
			System.out.println(roomCnt.get(0).toString());
		}
		
		return roomCnt;
	}

	@Override
	public List<RoomBVO> getRoomList(HttpServletRequest req) {
	//public List<RoomBVO> getRoomList(HttpServletRequest req, @RequestBody ListVO vo) {
		// TODO 매물 정보 가져오기
		
		String b_code = (req.getParameter("b_code") == "")? null : req.getParameter("b_code");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("b_code", b_code);
		System.out.println(map);
		List<RoomBVO> roomList = r_dao.getRoomList(map);
		
		if(roomList.size() <= 0) {
			System.out.println("데이터 없음");
		} else {
			System.out.println(roomList.get(0).toString());
		}
		
		return roomList;
	}
}
