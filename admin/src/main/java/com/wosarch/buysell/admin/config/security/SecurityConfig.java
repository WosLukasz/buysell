package com.wosarch.buysell.admin.config.security;

import com.wosarch.buysell.admin.filters.CsrfCookieFilter;
import com.wosarch.buysell.admin.filters.UserCreationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Profile("!prod")
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {

    String[] PUBLIC_API_ENDPOINTS_PATTERNS = new String[]{
//            "/patches/signatures-by-status"
    };

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(getCorsConfigurationSource()))
                .csrf(this::configureCsrf)
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(userCreationFilter(), AuthorizationFilter.class)
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP for localhost
                .oauth2ResourceServer(this::configureOauthResourceServer);

        return http.build();
    }

    @Bean
    public UserCreationFilter userCreationFilter() {
        return new UserCreationFilter();
    }

    @Bean
    public FilterRegistrationBean<UserCreationFilter> userCreationFilterRegistrationBean() {
        FilterRegistrationBean<UserCreationFilter> filterRegistrationBean = new FilterRegistrationBean(userCreationFilter());
        filterRegistrationBean.setEnabled(false);

        return filterRegistrationBean;
    }

    private CorsConfigurationSource getCorsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setExposedHeaders(Arrays.asList("Authorization"));
            config.setMaxAge(3600L);
            return config;
        };
    }

    private void configureOauthResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> rsc) {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        rsc.jwt(jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter));
    }

    private void configureCsrf(CsrfConfigurer<HttpSecurity> csrfConfig) {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        csrfConfig
                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                .ignoringRequestMatchers(PUBLIC_API_ENDPOINTS_PATTERNS)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }


}