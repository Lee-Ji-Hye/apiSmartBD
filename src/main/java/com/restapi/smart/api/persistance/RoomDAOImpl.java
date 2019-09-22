package com.restapi.smart.api.persistance;

import java.util.List;
import java.util.Map;

import com.restapi.smart.api.vo.RoomBVO;
import com.restapi.smart.api.vo.RoomContractDetailVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomDAOImpl implements RoomDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<RoomBVO> getRoomCnt() {
		// TODO 매물 수량 가져오기
		return sqlSession.selectList("RoomDAO.getRoomCnt");
	}

	@Override
	public List<RoomBVO> getRoomList(Map<String, Object> map) {
		// TODO 매물 정보 가져오기
		System.out.println("roomList : " + map);
		return sqlSession.selectList("RoomDAO.getRoomList", map);
	}

	@Override
	public int getRoomImageCnt(Map<String, Object> map) {
		// TODO 매물 사진 수량 가져오기
		return sqlSession.selectOne("RoomDAO.getRoomImageCnt", map);
	}

	@Override
	public List<String> getRoomImageFile(Map<String, Object> map) {
		// TODO 매물 사진 가져오기
		System.out.println("roomList : " + map);
		return sqlSession.selectList("RoomDAO.getRoomImageFile", map);
	}

	@Override
	public int insertContract(RoomContractDetailVO vo) {
		// TODO 계약 정보 등록
		return sqlSession.insert("RoomDAO.insertContract", vo);
	}

	@Override
	public int selectContract(String rt_code) {
		// TODO 계약 정보 조회
		return sqlSession.selectOne("RoomDAO.selectContract", rt_code);
	}

	@Override
	public List<RoomContractDetailVO> getContractList(Map<String, Object> map) {
		// TODO 계약 정보 가져오기
		System.out.println("contractList : " + map);
		return sqlSession.selectList("RoomDAO.getContractList", map);
	}

}
