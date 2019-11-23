package org.smr.ministore.entities;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.io.Serializable;
import java.util.List;

/**
 * 网关路由
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GatewayRoute implements Serializable {

    private String id;

    private String uri;

    private List<GatewayPredicate> predicates;

    private List<GatewayFilter> filters;

    private int order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }


    public List<GatewayPredicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<GatewayPredicate> predicates) {
        this.predicates = predicates;
    }

    public List<GatewayFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<GatewayFilter> filters) {
        this.filters = filters;
    }


    @Override
    public String toString() {
        return "GatewayDefine{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", predicates='" + predicates + '\'' +
                ", filters='" + filters + '\'' +
                '}';
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


}

