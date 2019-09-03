package com.restapi.smart.security;

import com.restapi.smart.persistance.UserDAO;
import com.restapi.smart.security.domain.Account;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Component
public class AccountContextService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AccountContext.class);

    //TODO MYBATIS로 바꿔야될 부분
    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        log.info("loadUserByUsername:"+userid);

        Account account = userDAO.selectMember(userid);
        //orElseThrow(() -> new NoSuchElementException("아이디에 맞는 계정이 없습니다."));

        return getAccountContext(account);
    }

    private AccountContext getAccountContext(Account account)
    {
        return AccountContext.fromAccountModel(account);
    }
}