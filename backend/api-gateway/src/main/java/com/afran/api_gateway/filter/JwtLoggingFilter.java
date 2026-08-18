package com.afran.api_gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtLoggingFilter implements GlobalFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .cast(Authentication.class)
                .doOnNext(authentication -> {
                    if(authentication instanceof JwtAuthenticationToken jwtAuthentication) {
                        String username = jwtAuthentication
                                .getToken()
                                .getSubject();

                        log.info(
                                "JWT validated successfully | method={} | path={} | user={}",
                                exchange.getRequest().getMethod(),
                                exchange.getRequest().getPath(),
                                username
                        );
                    }
                })
                .then(chain.filter(exchange));
    }
}
