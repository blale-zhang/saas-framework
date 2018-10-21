package org.blade.service.base;

import org.blade.entities.base.BaseEntity;
import org.blade.utils.Pager;

/**
 * 分页查询
 * @param <T>
 */
public interface PaginationQueryService<ID,T extends BaseEntity<ID>>  {

    public void paginate(Pager<T> page, T entity );
}
