package com.restapi.smart.security.services.impl;


import com.restapi.smart.security.domain.SocialProviders;
import com.restapi.smart.security.dtos.SocialLoginDto;
import com.restapi.smart.security.services.specification.SocialFetchService;
import com.restapi.smart.security.social.SocialUserProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SocialFetchServiceProd implements SocialFetchService {

    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    public SocialUserProperty getSocialUserInfo(SocialLoginDto dto) {
        SocialProviders provider = dto.getProvider();
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>("parameter", generateHeader(dto.getToken()));

        return restTemplate.exchange(provider.getUserinfoEndpoint(), HttpMethod.GET, entity, provider.getPropertyMetaclass()).getBody();
    }

    private HttpHeaders generateHeader(String token) {
        HttpHeaders header = new HttpHeaders();

        header.add("Authorization", generateHeaderContent(token));
        return header;
    }

    private String generateHeaderContent(String token) {
        StringBuilder sb = new StringBuilder();

        sb.append(HEADER_PREFIX);
        sb.append(token);

        return sb.toString();
    }
}