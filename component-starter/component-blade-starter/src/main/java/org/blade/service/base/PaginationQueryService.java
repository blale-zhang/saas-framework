package org.blade.service.base;

import org.blade.entities.base.BaseEntity;
import org.blade.utils.Pager;

/**
 * 分页查询
 * @param <T>
 */
public interface PaginationQueryService<ID,T extends BaseEntity<ID>>  {

    public Pager<T> paginate( T entity, Long pageSize, Long pageNum );
}
