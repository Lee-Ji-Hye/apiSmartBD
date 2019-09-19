package com.restapi.smart.api.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.service.RoomService;
import com.restapi.smart.api.util.ApiResponseStatus;
import com.restapi.smart.api.vo.RoomBVO;
import com.restapi.smart.api.vo.RoomContractDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author myungkeun
 *
 */
@Slf4j
@RestController
@RequestMapping("api/room")
public class RoomControllerAPI {
	HashMap<String, Object> res;

	@Autowired
	RoomService r_service;

	@Autowired
	ApiResponseStatus code_status;

	/*****************************************************************
	 *
	 * API 영역
	 *
	 *****************************************************************/

	//매물 수량
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

	//계약 정보 등록
	@PostMapping(value = "insertContract")
	public HashMap<String, Object> insertContract(@RequestBody RoomContractDetailVO vo) {
		log.info("계약 정보 등록", vo);
		System.out.println(vo);

		int result = r_service.insertContract(vo);
		String msg = code_status.responseMsg(result);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("responseCode", result);
		map.put("responseMsg", msg);

		return map;
	}

	//계약 정보 리스트
	@RequestMapping(value="getContractList") //통신 사용시 이걸로 사용.
	public HashMap<String, Object> getContractList(HttpServletRequest req) {

		List<RoomContractDetailVO> result = r_service.getContractList(req);

		HashMap<String, Object> map = new HashMap<String, Object>();
		if(result.size() > 0) {
			map.put("contracts", result);
			map.put("responseCode", 100);
			map.put("responseMsg", "성공");
		} else {
			map.put("contracts", null);
			map.put("responseCode", 999);
			map.put("responseMsg", "데이터 없음");
		}
		return map;
	}
}
