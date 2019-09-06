package com.restapi.smart.api.persistance;

import java.util.List;
import java.util.Map;

import com.restapi.smart.api.vo.RoomBVO;
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
		System.out.println("룸리스트 : " + map);
		return sqlSession.selectList("RoomDAO.getRoomList", map);
	}

}
