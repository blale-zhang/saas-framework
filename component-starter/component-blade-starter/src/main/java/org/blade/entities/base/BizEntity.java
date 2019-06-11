package org.blade.entities.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 业务实体
 * @param <ID>
 */
public class BizEntity<ID> extends BaseEntity<ID> {



    @JSONField(serializeUsing = ToStringSerializer.class)
    private ID organId;

    public ID getOrganId() {
        return organId;
    }

    public void setOrganId(ID organId) {
        this.organId = organId;
    }


    public String organPath;

    public String getOrganPath() {
        return organPath;
    }

    public void setOrganPath(String organPath) {
        this.organPath = organPath;
    }
}
