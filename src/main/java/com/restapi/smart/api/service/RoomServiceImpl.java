package com.restapi.smart.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.restapi.smart.api.persistance.RoomDAO;
import com.restapi.smart.api.util.Functions;
import com.restapi.smart.api.util.Local;
import com.restapi.smart.api.vo.RoomBVO;
import com.restapi.smart.api.vo.RoomContractDetailVO;
import com.restapi.smart.api.vo.RoomImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomDAO r_dao;

	@Autowired
	Functions fn;

	@Autowired
	Local local;

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
	public List<String> getRoomImage(HttpServletRequest req) {
		// TODO 매물 사진 가져오기

		String b_code = (req.getParameter("b_code") == "")? null : req.getParameter("b_code");
		String r_code = (req.getParameter("r_code") == "")? null : req.getParameter("r_code");

		System.out.println("b_code : "+b_code);
		System.out.println("r_code : "+r_code);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("b_code", b_code);
		map.put("r_code", r_code);

		//int roomImageCnt = r_dao.getRoomImageCnt(map);
		List<String> temp = r_dao.getRoomImageFile(map); //

		List<String> roomImage = null;

		if(temp != null) {
			roomImage = new ArrayList<String>();
			for(int i=0; i<temp.size(); i++) {
				//"http://"+ip+"/smart/resources/images/food/"+f_mainimg;
				String imgUrl = "http://"+local.getIP()+"/smart/resources/images/room/"+temp.get(i);
				roomImage.add(imgUrl);
			}
		}

		//System.out.println("roomImageCnt : "+roomImageCnt);
		System.out.println("roomImage : "+roomImage);

		//RoomImageVO roomImageVO= new RoomImageVO();
		//roomImageVO.setRoomImgCnt(Integer.toString(roomImageCnt));
		//roomImageVO.setRoomImg(roomImage);

		return roomImage;
	}

	@Override
	public int insertContract(RoomContractDetailVO vo) {
		// TODO 계약 정보 등록
		int result = 599; //결과 초기값 : 등록 실패

		if(vo == null) {
			return 501; //파라미터 부재
		}

		String rt_code = fn.mkUniquecode("rt_code", "room_contract_tbl");
		String comp_seq = fn.mkUniquecode("comp_seq", "user_compauth_tbl");

		vo.setRt_code(rt_code);
		vo.setComp_seq(comp_seq);

		r_dao.insertContract(vo);

		//TODO 계약 정보 조회
		int selectCnt = r_dao.selectContract(rt_code);
		int insertCnt = 0;

		if(selectCnt > 0) {
			//TODO 임차인 권한 등록
			//id, 업체코드, 권한명, insert 이거 승인되면 넣을예정
			HashMap<String,String> map = new HashMap<>();
			map.put("userid", vo.getUserid());
			map.put("comp_auth", "ROLE_CP_TENANT");
			map.put("rt_code", vo.getRt_code());
			//map.put("b_code", vo.getB_code());
			//map.put("comp_seq", vo.getComp_seq());

			System.out.println("임차인 권한 등록 : "+map);
			insertCnt = r_dao.insertAuth(map);
		}

		if(insertCnt > 0) {
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

	@Override
	public int insertPay(RoomContractDetailVO vo) {
		// TODO 납부 정보 등록
		int result = 699; //결과 초기값 : 등록 실패

		if(vo == null) {
			return 601; //파라미터 부재
		}

		int insertCnt = 0;
		insertCnt = r_dao.insertPay(vo);

		if(insertCnt > 0) {
			result = 600;
		}

		return result;
	}
}
