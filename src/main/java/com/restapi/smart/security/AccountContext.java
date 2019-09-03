package com.restapi.smart.security;

import com.restapi.smart.security.domain.Account;
import com.restapi.smart.security.domain.UserGrantedAuthority;
import com.restapi.smart.security.domain.UserRole;
import com.restapi.smart.security.token.JwtPostProcessingToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountContext extends User {

    private Account account;

    private static final Logger log = LoggerFactory.getLogger(AccountContext.class);

    private AccountContext(Account account, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.account = account;
    }

    public AccountContext(String username, String password, String role) {
        super(username, password, parseAuthorities(role));
    }

    //TODO mybatis변경으로 account 메소드도 변경됨
    public static AccountContext fromAccountModel(Account account) {
        ArrayList<UserGrantedAuthority> list = new ArrayList<>();
        if(account.getAuth().size()==0){
            list.add(new UserGrantedAuthority("ROLE_DEFAULT",null,null));
            account.setAuth(list);
        }
        log.info("fromAccountModel()" + account.getAuth());
        log.info("fromAccountModel()" + account);
        return new AccountContext(account, account.getUserid(), account.getUserpw(), account.getAuth());
    }

    public static AccountContext fromJwtPostToken(JwtPostProcessingToken token) {
        return new AccountContext(null, token.getUserid(), token.getPassword(), token.getAuthorities());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(UserRole role) {
        return Arrays.asList(role).stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(String role) {
        log.info(parseAuthorities(UserRole.getRoleByName(role)).toString());
        return parseAuthorities(UserRole.getRoleByName(role));
    }

    public Account getAccount() {
        return account;
    }
}