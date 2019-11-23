package org.smr.ministore.endpoint;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


@Service
public class DynamicRouteService  implements ApplicationEventPublisherAware {


    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private RedisTemplate stringRedisTemplate;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private void notifyChanged(){

        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 增加路由
     *
     */
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        notifyChanged();
        return "success";
    }


    /**
     * 更新路由
     */
    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            notifyChanged();
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }


    }

    /**
     * 删除路由
     *
     */
    public Mono delete(String id) {
        try {
            Mono mono = this.routeDefinitionWriter.delete(Mono.just(id));
            notifyChanged();
           return mono;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
