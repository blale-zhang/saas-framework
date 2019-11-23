package org.smr.ministore.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GatewayPredicate {

    //断言对应的Name
    private String name;
    //配置的断言规则
    private Map<String, String> args = new LinkedHashMap<>();
    //此处省略Get和Set方法

}
