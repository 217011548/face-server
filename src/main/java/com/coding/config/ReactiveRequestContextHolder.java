package com.coding.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;


/**
 * @author guanweiming
 */
@Slf4j
public class ReactiveRequestContextHolder {
    public static final Class<ServerHttpRequest> CONTEXT_KEY = ServerHttpRequest.class;

    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(CONTEXT_KEY));
    }

    public static Mono<Long> getUserId() {
        return getRequest()
                .map(ReactiveRequestContextHolder::decodeUserId);
    }

    private static Long decodeUserId(ServerHttpRequest request) {
        String userId = "userId";
        return NumberUtils.toLong(request.getHeaders().getFirst(userId));
    }
}
