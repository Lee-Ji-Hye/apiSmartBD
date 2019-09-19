package com.restapi.smart.api.persistance;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CodeDAOImpl implements CodeDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int getLastIdx(Map<String, Object> map) {
		// TODO 유니크 코드를 생성하기 위해서, 코드의 마지막 인덱스+1을 가져오기
		System.out.println("~~~~~~~~~" +  map);
		return sqlSession.selectOne("CodeDAO.getLastIdx", map);
	}
	
}