package com.restapi.smart.security.services.specification;


import com.restapi.smart.security.dtos.SocialLoginDto;
import com.restapi.smart.security.social.SocialUserProperty;

public interface SocialFetchService {

    SocialUserProperty getSocialUserInfo(SocialLoginDto dto);

}