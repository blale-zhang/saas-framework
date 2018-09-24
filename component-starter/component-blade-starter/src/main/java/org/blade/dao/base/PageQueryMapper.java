/**
 * 
 */
package org.blade.dao.base;

import org.blade.entities.base.BaseEntity;
import org.blade.utils.Pager;


/**
 * @author blade
 *
 */
public interface PageQueryMapper<ID,T extends BaseEntity<ID>>{

	public void paginate(T entity, Pager<T> page);
}
