package com.wosarch.buysell.auctions.common.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * We assume that we call only services in our private network. No communication to an external services.
 * Interceptor works like bellow:
 * If current SecurityContextHolder has authentication then use existing access token (for example if some call from front-end via API GATEWAY). Thanks to this we can check user rights in called services.
 * Else get access token of tech account (this is only when call is not triggered by user or other service but comes from this microservice. For example during patches installation PatchService is called) Thanks to this we can call other services an has access to theirs endpoints without Access denied error.
 */
@Component
public class JwtTokenPropagator implements RequestInterceptor {

    private static final String AUTHORIZATION_TYPE = "Bearer ";
    private static final String BASIC_AUTHORIZATION_TYPE = "BASIC ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${buySell.config.techAccount.clientId}")
    private String techAccountClientId;

    @Value("${buySell.config.techAccount.secretKey}")
    private String techAccountSecretKey;

    @Autowired
    private OauthFeignTokenService oauthFeignTokenService;

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof Jwt) {
            String jwtToken =  ((Jwt) authentication.getCredentials()).getTokenValue();
            template.header(AUTHORIZATION_HEADER, AUTHORIZATION_TYPE.concat(jwtToken));
        } else {
            Map<String, String> request = new TreeMap<>();
            request.put("grant_type", "client_credentials");
            String tokenBase64 = Base64.getEncoder().encodeToString((techAccountClientId + ":" + techAccountSecretKey).getBytes());
            AccessTokenResponse token = oauthFeignTokenService.getToken(request, BASIC_AUTHORIZATION_TYPE.concat(tokenBase64));
            template.header(AUTHORIZATION_HEADER, AUTHORIZATION_TYPE.concat(token.getToken()));
        }
    }
}