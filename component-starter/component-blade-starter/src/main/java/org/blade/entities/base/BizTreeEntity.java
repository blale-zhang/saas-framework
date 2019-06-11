package org.blade.entities.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
 *
 * @param <ID>
 */
public class BizTreeEntity<ID> extends BizEntity<ID> {


    @JSONField(serializeUsing = ToStringSerializer.class)
    private ID parentId;

    public ID getParentId() {
        return parentId;
    }

    public void setParentId(ID parentId) {
        this.parentId = parentId;
    }
}
