package com.restapi.smart.api.persistance;

import java.util.List;
import java.util.Map;

import com.restapi.smart.api.vo.RoomBVO;

public interface RoomDAO {
	
	//매물 수량 가져오기
	public List<RoomBVO> getRoomCnt();
	
	//매물 정보 가져오기
	public List<RoomBVO> getRoomList(Map<String, Object> map);
}
