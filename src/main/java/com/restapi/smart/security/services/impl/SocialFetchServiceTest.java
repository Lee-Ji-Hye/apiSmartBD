package com.restapi.smart.security.services.impl;


import com.restapi.smart.security.dtos.SocialLoginDto;
import com.restapi.smart.security.services.specification.SocialFetchService;
import com.restapi.smart.security.social.SocialUserProperty;
import org.springframework.stereotype.Service;

@Service
public class SocialFetchServiceTest implements SocialFetchService {

    @Override
    public SocialUserProperty getSocialUserInfo(SocialLoginDto dto) {
        return null;
    }
}