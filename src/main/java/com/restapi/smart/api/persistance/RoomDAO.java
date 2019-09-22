package com.restapi.smart.api.persistance;

import java.util.List;
import java.util.Map;

import com.restapi.smart.api.vo.RoomBVO;
import com.restapi.smart.api.vo.RoomContractDetailVO;

public interface RoomDAO {

	//매물 수량 가져오기
	public List<RoomBVO> getRoomCnt();

	//매물 정보 가져오기
	public List<RoomBVO> getRoomList(Map<String, Object> map);

	//매물 사진 수량 가져오기
	public int getRoomImageCnt(Map<String, Object> map);

	//매물 사진 가져오기
	public List<String> getRoomImageFile(Map<String, Object> map);

	//계약 정보 등록
	public int insertContract(RoomContractDetailVO vo);

	//계약 정보 조회
	public int selectContract(String rt_code);

	//계약 정보 가져오기
	public List<RoomContractDetailVO> getContractList(Map<String, Object> map);
}
