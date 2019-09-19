package com.restapi.smart.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.persistance.RoomDAO;
import com.restapi.smart.api.util.Functions;
import com.restapi.smart.api.vo.RoomBVO;
import com.restapi.smart.api.vo.RoomContractDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomDAO r_dao;

	@Autowired
	Functions fn;

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

	@Override
	public int insertContract(RoomContractDetailVO vo) {
		// TODO 계약 정보 등록
		int result = 599; //결과 초기값 : 등록 실패

		if(vo == null) {
			return 501; //파라미터 부재
		}

		String rt_code = fn.mkUniquecode("rt_code", "room_contract_tbl");

		vo.setRt_code(rt_code);

		r_dao.insertContract(vo);

		int selectCnt = r_dao.selectContract(rt_code); //계약 정보 조회

		if(selectCnt > 0) {
			result = 500;
		}
		return result;
	}

	@Override
	public List<RoomContractDetailVO> getContractList(HttpServletRequest req) {
		// TODO 계약 정보 가져오기

		String userid = (req.getParameter("userid") == "")? null : req.getParameter("userid");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		System.out.println(map);
		List<RoomContractDetailVO> contractList = r_dao.getContractList(map);

		if(contractList.size() <= 0) {
			System.out.println("데이터 없음");
		} else {
			System.out.println(contractList.get(0).toString());
		}

		return contractList;
	}
}
