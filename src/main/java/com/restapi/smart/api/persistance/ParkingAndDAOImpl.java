package com.restapi.smart.api.persistance;

import java.util.List;

import com.restapi.smart.api.vo.ParkingBDVO;
import com.restapi.smart.api.vo.ParkingTicketVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ParkingAndDAOImpl implements ParkingAndDAO{
	//주차관리 안드로이드 DAO
	
	@Autowired
	private SqlSession sqlSession;

	//주차장 전체 건물 정보
	@Override
	public ParkingBDVO getBDInfo(String b_code) {
		return sqlSession.selectOne("ParkingAndDAO.getBDInfo",b_code);
	}

	//하나의 건물정보 가져오기 
	@Override
	public List<ParkingBDVO> getBDList() {
		return sqlSession.selectList("ParkingAndDAO.getBDList");
	}

	//하나의 건물 주차권 정보 가져오기
	@Override
	public List<ParkingTicketVO> getTicketList(String b_code) {
		return sqlSession.selectList("ParkingAndDAO.getTicketList",b_code);
	}

}