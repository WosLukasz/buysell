package com.wosarch.buysell.admin.config.security;

import com.wosarch.buysell.admin.filters.UserCreationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@Profile("!prod")
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(AbstractHttpConfigurer::disable) // handled by API GATEWAY
                .csrf(AbstractHttpConfigurer::disable) // handled by API GATEWAY
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

    private void configureOauthResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> rsc) {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        rsc.jwt(jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter));
    }
}