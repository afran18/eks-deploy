package com.afran.api_gateway.filter;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingGlobalFilter.class);

    @PostConstruct
    public void init() {
        log.info("### LoggingGlobalFilter initialized ###");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        log.info(">>> Incoming request: [{}] {} | Query: {} | Headers: {}",
                request.getMethod(),
                request.getURI(),
                request.getQueryParams(),
                request.getHeaders());

        long start = System.currentTimeMillis();

        return chain.filter(exchange).doFinally(signal -> {
            Route route = exchange.getAttribute(
                    org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            URI routedUri = route != null ? route.getUri() : null;
            int status = exchange.getResponse().getStatusCode() != null
                    ? exchange.getResponse().getStatusCode().value() : -1;

            log.info("<<< Response: [{}] {} -> matchedRoute={} routedTo={} status={} tookMs={}",
                    request.getMethod(),
                    request.getURI(),
                    route != null ? route.getId() : "NO_ROUTE_MATCHED",
                    routedUri,
                    status,
                    System.currentTimeMillis() - start);
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}