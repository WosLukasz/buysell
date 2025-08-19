package com.wosarch.buysell.auctions.common.config.feign;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import java.util.Map;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(name = "OauthFeignTokenService", url = "${keycloak.token-uri-prefix}", configuration = OauthFeignTokenService.Configuration.class)
public interface OauthFeignTokenService {

    @PostMapping(value = "/token", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    AccessTokenResponse getToken(@RequestBody Map<String, ?> form, @RequestHeader("Authorization") String auth);

    class Configuration {

        @Bean
        Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}
