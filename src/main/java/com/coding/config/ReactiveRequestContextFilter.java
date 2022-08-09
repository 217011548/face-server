package com.coding.config;

import com.coding.utils.JsonUtil;
import com.coding.utils.JwtTokenUtils;
import com.coding.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveRequestContextFilter implements WebFilter {

    @NotNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        ServerHttpRequest httpRequest = exchange.getRequest();
        String token = httpRequest.getHeaders().getFirst("Authorization");
        Map<String, String> header = httpRequest.getHeaders().toSingleValueMap();
        log.info("token:{}:{}", token, header);
        ServerHttpRequest request = httpRequest;
        if (StringUtils.isNotBlank(token)) {
            boolean isToken = JwtTokenUtils.isToken(token);
            if (!isToken) {
                return errorInfo(exchange, R.createByNeedLogin());
            }
            token = token.replace(JwtTokenUtils.TOKEN_PREFIX, "");
            if (JwtTokenUtils.isExpiration(token)) {
                return errorInfo(exchange, R.createByNeedLogin());
            }
            String userId = JwtTokenUtils.getUsername(token);
            Set<String> roles = JwtTokenUtils.getUserRole(token);
            log.info("userId:{}", userId);
            log.info("roles:{}", StringUtils.join(roles, ","));
            request = httpRequest.mutate()
                    .header("userId", userId)
                    .header("role", StringUtils.join(roles, ","))
                    .build();
        }
        ServerHttpRequest finalRequest = request;
        return chain.filter(exchange).contextWrite(ctx -> ctx.put(ReactiveRequestContextHolder.CONTEXT_KEY, finalRequest));
    }

    /**
     * response
     *
     * @param exchange context filter
     */
    public Mono<Void> errorInfo(ServerWebExchange exchange, Object result) {
        // set the retrun format
        return Mono.defer(() -> {
            byte[] bytes = new byte[0];
            try {
                bytes = JsonUtil.getObjectMapper().writeValueAsBytes(result);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(buffer));
        });
    }
}
