package org.smr.ministore.gateway.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {


    public static final String GATEWAY_ROUTES = "geteway_routes";

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {

        List<RouteDefinition> routeDefinitions = new ArrayList<>();

        Map<String,Object> rotues = redisTemplate.boundHashOps(GATEWAY_ROUTES).entries();

        for(Map.Entry<String,Object> entry: rotues.entrySet()){

            routeDefinitions.add(JSON.parseObject(entry.getValue().toString(),
                    RouteDefinition.class));
        }

        return Flux.fromIterable(routeDefinitions);

    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {

        return route.flatMap((r) -> {

            redisTemplate.boundHashOps(GATEWAY_ROUTES).put(r.getId(),JSON.toJSONString(r));

            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap((id) -> {
            if (this.redisTemplate.boundHashOps(GATEWAY_ROUTES).hasKey(id)) {
                this.redisTemplate.boundHashOps(GATEWAY_ROUTES).delete(id);
                return Mono.empty();
            } else {
                return Mono.defer(() -> {
                    return Mono.error(new NotFoundException("RouteDefinition not found: " + routeId));
                });
            }
        });
    }
}
