package com.restapi.smart.persistance;

import com.restapi.smart.security.domain.Account;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    SqlSession sqlSession;

    @Override
    public Account selectMember(String userid) {
        return sqlSession.selectOne("UserDAO.selectMember", userid);
    }
}
