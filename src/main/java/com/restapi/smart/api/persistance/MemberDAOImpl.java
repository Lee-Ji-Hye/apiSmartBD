package com.restapi.smart.api.persistance;

import com.restapi.smart.api.vo.UserVO;
import com.restapi.smart.security.domain.Account;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public int selectUserId(String userid) {
        return sqlSession.selectOne("MemberDAO.selectUserId", userid);
    }

    @Override
    public String selectUserPW(String userpw) {
        return sqlSession.selectOne("MemberDAO.selectUserPW", userpw);
    }

    @Override
    public Account selectMember(String userid) {
        return sqlSession.selectOne("MemberDAO.selectMember", userid);
    }

    @Override
    public int insertMember(UserVO vo) {
        return sqlSession.insert("MemberDAO.insertMember", vo);
    }

    @Override
    public UserVO getUserInfo(String userid) {
        return sqlSession.selectOne("MemberDAO.getUserInfo", userid);
    }


}
