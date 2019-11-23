package org.smr.ministore.endpoint;

import org.smr.ministore.entities.GatewayFilter;
import org.smr.ministore.entities.GatewayPredicate;
import org.smr.ministore.entities.GatewayRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
public class DynamicRouteController {

    @Autowired
    private DynamicRouteService dynamicRouteService;



    @Autowired private RouteDefinitionLocator routeDefinitionLocator;

    //获取网关所有的路由信息
    @RequestMapping("/routes")
    public Flux<RouteDefinition> getRouteDefinitions(){
        Flux<RouteDefinition> routeDefinitionFlux =  routeDefinitionLocator.getRouteDefinitions();

        return routeDefinitionFlux;
    }

    //增加路由
    @PostMapping("/add")
    public String add(@RequestBody GatewayRoute gwdefinition) {
        String flag = "fail";
        try {
            RouteDefinition definition = assembleRouteDefinition(gwdefinition);
            flag = this.dynamicRouteService.add(definition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    //删除路由
    @DeleteMapping("/routes/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        try {
            return this.dynamicRouteService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //更新路由
    @PostMapping("/update")
    public String update(@RequestBody GatewayRoute gatewaydefinition) {
        RouteDefinition definition = assembleRouteDefinition(gatewaydefinition);
        return this.dynamicRouteService.update(definition);
    }

    //把传递进来的参数转换成路由对象
    private RouteDefinition assembleRouteDefinition(GatewayRoute route) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(route.getId());
        definition.setOrder(route.getOrder());

        //设置断言
        List<PredicateDefinition> pdList=new ArrayList<>();
        List<GatewayPredicate> gatewayPredicateDefinitionList = route.getPredicates();
        for (GatewayPredicate predicatefinition: gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(predicatefinition.getArgs());
            predicate.setName(predicatefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);

        //设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilter> gatewayFilters = route.getFilters();
        for(GatewayFilter filterDefinition : gatewayFilters){
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);

        URI uri = null;
        if(route.getUri().startsWith("http")){
            uri = UriComponentsBuilder.fromHttpUrl(route.getUri()).build().toUri();
        }else{
            // uri为 lb://consumer-service 时使用下面的方法
            uri = URI.create(route.getUri());
        }
        definition.setUri(uri);
        return definition;
    }
}
