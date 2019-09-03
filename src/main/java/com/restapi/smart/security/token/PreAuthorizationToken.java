package com.restapi.smart.security.token;


import com.restapi.smart.security.domain.SocialProviders;
import com.restapi.smart.security.dtos.FormLoginDto;
import com.restapi.smart.security.dtos.SocialLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    private PreAuthorizationToken(SocialProviders providers, SocialLoginDto dto) {
        super(providers, dto);
    }

    public PreAuthorizationToken(FormLoginDto dto) {
        this(dto.getId(), dto.getPassword());
    }

    public PreAuthorizationToken(SocialLoginDto dto) {
        this(dto.getProvider(), dto);
    }

    public String getUsername() {
        return (String)super.getPrincipal();
    }

    public String getUserPassword() {
        return (String)super.getCredentials();
    }

}