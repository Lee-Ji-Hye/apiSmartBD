package com.restapi.smart.api.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.service.RoomService;
import com.restapi.smart.api.vo.RoomBVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author myungkeun
 *
 */
@Slf4j
@RestController
@RequestMapping("api/room")
public class RoomControllerAPI {
	
	@Autowired
	RoomService r_service;
	
	/*****************************************************************
	 * 
	 * API 영역 
	 *
	 *****************************************************************/
	
	//매물 수량
	@ResponseBody
	@RequestMapping(value="getRoomCnt") //통신 사용시 이걸로 사용.
	public HashMap<String, Object> getRoomCnt(HttpServletRequest req) {
		
		List<RoomBVO> result = r_service.getRoomCnt(req);

		HashMap<String, Object> map = new HashMap<String, Object>();
		if(result.size() > 0) {
			map.put("rooms", result);
			map.put("responseCode", 100);
			map.put("responseMsg", "성공");
		} else {
			map.put("rooms", null);
			map.put("responseCode", 999);
			map.put("responseMsg", "데이터 없음");
		}
		return map;
	}
	
	//매물 정보 리스트
	@ResponseBody
	@RequestMapping(value="getRoomList") //통신 사용시 이걸로 사용.
	public HashMap<String, Object> getRoomList(HttpServletRequest req) {
		
		List<RoomBVO> result = r_service.getRoomList(req);

		HashMap<String, Object> map = new HashMap<String, Object>();
		if(result.size() > 0) {
			map.put("rooms", result);
			map.put("responseCode", 100);
			map.put("responseMsg", "성공");
		} else {
			map.put("rooms", null);
			map.put("responseCode", 999);
			map.put("responseMsg", "데이터 없음");
		}
		return map;
	}
}
