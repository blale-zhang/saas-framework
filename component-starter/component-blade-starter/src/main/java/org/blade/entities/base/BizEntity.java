package org.blade.entities.base;

/**
 * 业务实体
 * @param <ID>
 */
public class BizEntity<ID> extends BaseEntity<ID> {

    private ID organId;

    public ID getOrganId() {
        return organId;
    }

    public void setOrganId(ID organId) {
        this.organId = organId;
    }
}
