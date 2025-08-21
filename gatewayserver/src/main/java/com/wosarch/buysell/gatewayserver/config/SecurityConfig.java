package com.wosarch.buysell.gatewayserver.config;

import com.wosarch.buysell.gatewayserver.filters.CsrfCookieFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.util.matcher.AndServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_API_ENDPOINTS_PATTERNS = new String[] {
            "/buysell-api/admin/public/**",
            "/buysell-api/attachments/public/**",
            "/buysell-api/auctions/public/**"
    };

    @Value("${buySell.cors.allowedOrigins.fontEnd}")
    private String frontEndUrl;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .authorizeExchange(getAuthorizeExchange())
                .oauth2ResourceServer(getOauth2ResourceServerConfig())
                .cors(corsConfig -> corsConfig.configurationSource(getCorsConfigurationSource()))
                .csrf(configureCsrf())
                .addFilterAfter(new CsrfCookieFilter(), SecurityWebFiltersOrder.CSRF);

        return serverHttpSecurity.build();
    }

    private Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> getAuthorizeExchange() {
        return exchanges -> exchanges
                .pathMatchers("/buysell-api/admin/**").permitAll()
                .pathMatchers("/buysell-api/attachments/**").permitAll()
                .pathMatchers("/buysell-api/auctions/**").permitAll()
                .pathMatchers("/**").permitAll();
    }

    private Customizer<ServerHttpSecurity.OAuth2ResourceServerSpec> getOauth2ResourceServerConfig() {
        return oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor()));
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    // other way to turn on cors protection
//    @Bean
//    UrlBasedCorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList(frontEndUrl));
//        configuration.setAllowedMethods(Collections.singletonList("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }

    private CorsConfigurationSource getCorsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList(frontEndUrl));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setExposedHeaders(Arrays.asList("Authorization"));
            config.setMaxAge(3600L);

            return config;
        };
    }

    private Customizer<ServerHttpSecurity.CsrfSpec> configureCsrf() {
        return csrfConfig -> csrfConfig
                .csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler())
                .requireCsrfProtectionMatcher(getCsrfRequestMatcher())
                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse());
    }

    private AndServerWebExchangeMatcher getCsrfRequestMatcher() {
        return new AndServerWebExchangeMatcher(
                CsrfWebFilter.DEFAULT_CSRF_MATCHER,
                new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, PUBLIC_API_ENDPOINTS_PATTERNS)),
                new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE, PUBLIC_API_ENDPOINTS_PATTERNS)),
                new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.PUT, PUBLIC_API_ENDPOINTS_PATTERNS)),
                new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.PATCH, PUBLIC_API_ENDPOINTS_PATTERNS))
        );
    }

}