package com.restapi.smart.security.providers;


import com.restapi.smart.security.domain.Account;
import com.restapi.smart.security.domain.SocialProviders;
import com.restapi.smart.security.dtos.SocialLoginDto;
import com.restapi.smart.security.AccountContext;
import com.restapi.smart.security.services.specification.SocialFetchService;
import com.restapi.smart.security.social.SocialUserProperty;
import com.restapi.smart.security.token.PostAuthorizationToken;
import com.restapi.smart.security.token.SocialPreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {

    //@Autowired
    //private AccountRepository accountRepository;

    @Qualifier("socialFetchServiceProd")
    @Autowired
    private SocialFetchService service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken)authentication;
        SocialLoginDto dto = token.getDto();

        return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(getAccount(dto)));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(aClass);
    }

    private Account getAccount(SocialLoginDto dto) {
        SocialUserProperty property = service.getSocialUserInfo(dto);

        String userId = property.getUserId();
        SocialProviders provider = dto.getProvider();
        return null;
//        return accountRepository.findBySocialIdAndSocialProvider(Long.valueOf(userId), provider)
//                .orElseGet(() -> accountRepository.save(
//                        new Account(null, property.getUserNickname(), "SOCIAL_USER", String.valueOf(UUID.randomUUID().getMostSignificantBits()), UserRole.USER, Long.valueOf(property.getUserId()), provider, property.getProfileHref())));

    }

}