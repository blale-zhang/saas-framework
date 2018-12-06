/**
 * 
 */
package org.blade.dao.base;

import org.apache.ibatis.annotations.Param;
import org.blade.entities.base.BizEntity;
import org.blade.utils.Pager;

import java.util.List;


/**
 * @author blade
 *
 */
public interface PageQueryMapper<ID,T extends BizEntity<ID>>{

	public List<T> pagination(@Param("entity")T entity, @Param("page")Pager<T> page);
}
