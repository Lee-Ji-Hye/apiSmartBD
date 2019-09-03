package com.restapi.smart.security.token;


import com.restapi.smart.security.dtos.SocialLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SocialPreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public SocialPreAuthorizationToken(SocialLoginDto dto) {
        super(dto.getProvider(), dto);
    }

    public SocialLoginDto getDto() {
        return (SocialLoginDto)super.getCredentials();
    }
}