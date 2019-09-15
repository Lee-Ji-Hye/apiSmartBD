package com.restapi.smart.api.persistance;

import java.util.List;
import java.util.Map;

import com.restapi.smart.api.vo.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;


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

	//하나의 건물 하나의 주차권 정보 가져오기
	public List<ParkingTicketVO> getTicket(String p_code){
		return sqlSession.selectList("ParkingAndDAO.getTicket",p_code);
	}

	//주차권 주문 테이블 삽입
	@Override
	public void insertTicketOrder(ParkingOrderVO vo) {
		sqlSession.insert("ParkingAndDAO.insertTicketOrder",vo);
	}

	//주차권 주문 정보 변경
	@Override
	public void updatePakingOrder(Map map) {
		sqlSession.update("ParkingAndDAO.updatePakingOrder",map);
	}

	@Override
	public ParkingOrderVO getOrderInfo(String p_ocode) {
		System.out.println("DAO:"+p_ocode);
		return sqlSession.selectOne("ParkingAndDAO.getOrderInfo",p_ocode);
	}

	@Override
	public void updatePakingOrderSucecss(Map map) {
		sqlSession.update("ParkingAndDAO.updatePakingOrderSucecss",map);
	}

	//주차권 발급
	@Override
	public void insertTicketHistory(ParkingTickeHistoryVO pth_vo) {
		sqlSession.insert("ParkingAndDAO.insertTicketHistory", pth_vo);
	}

	@Override
	public List<UserCarVO> getUserInfo(String userid) {
		return sqlSession.selectList("ParkingAndDAO.getUserInfo",userid);
	}

	@Override
	public void insertCarinfo(UserCarVO vo) {
		sqlSession.insert("ParkingAndDAO.insertCarinfo",vo);
	}

	@Override
	public List<UserCarVO> getuserCarInfo(String userid) {
		return sqlSession.selectList("ParkingAndDAO.getuserCarInfo",userid);
	}

	//주차장 회원차량정보 삭제
	@Override
	public void delUserCarInfo(String userid) {
		sqlSession.delete("ParkingAndDAO.delUserCarInfo",userid);
	}

}